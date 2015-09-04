package com.ardoq.util;

import com.ardoq.ArdoqClient;
import com.ardoq.model.*;
import com.ardoq.service.*;
import com.google.gson.Gson;
import retrofit.RestAdapter;

import java.util.*;

/**
 * Created by magnulf on 23.04.14.
 */
public class SyncUtil {
    private final ArdoqClient client;
    private final WorkspaceService workspaceService;
    private Workspace workspace;
    private final ComponentService componentService;
    private final ReferenceService referenceService;
    private final TagService tagService;
    private final Model model;
    private final AttachmentService attachmentService;
    private AggregatedWorkspace aggregatedWorkspace;

    Gson gson = new Gson();

    private HashMap<String, Component> newComponents = new HashMap<String, Component>();
    private HashMap<String, Component> currentComponents = new HashMap<String, Component>();

    private HashMap<String, Component> componentTree = new HashMap<String, Component>();

    private HashMap<String, Tag> tags = new HashMap<String, Tag>();

    private HashMap<String, Reference> currentReferences = new HashMap<String, Reference>();
    private AbstractMap<String, Reference> referenceMap = new HashMap<String, Reference>();
    ;

    private HashMap<String, Reference> newRefs = new HashMap<String, Reference>();

    private Collection<BasicModel> modifiedList = new LinkedList<BasicModel>();
    private Collection<Tag> updatedTags = new LinkedList<Tag>();

    private int updatedRefCount = 0;
    private int updatedComponentCount = 0;
    private int deletedComponents = 0;
    private int deletedRefs = 0;


    public SyncUtil(ArdoqClient client, String workspaceName, String modelName) {
        this.client = client;
        this.client.setLogLevel(RestAdapter.LogLevel.NONE);
        this.model = client.model().getModelByName(modelName);
        this.workspaceService = client.workspace();
        this.componentService = client.component();
        this.referenceService = client.reference();
        this.attachmentService = client.attachment();
        this.tagService = client.tag();

        for (Workspace ws : this.workspaceService.getAllWorkspaces()) {
            if (ws.getName().equals(workspaceName) && ws.getComponentModel().equalsIgnoreCase(model.getId())) {
                this.workspace = ws;
                break;
            }
        }

        if (null == this.workspace) {
            this.workspace = workspaceService.createWorkspace(new Workspace(workspaceName, model.getId(), ""));
        } else {
            loadCurrentAggregatedWorkspace();
        }
    }

    private void loadCurrentAggregatedWorkspace() {
        this.aggregatedWorkspace = this.workspaceService.getAggregatedWorkspace(workspace.getId());
        this.createComponentTree();
        this.createReferenceMap();
        this.createTagMap();
    }

    private void createTagMap() {
        for (Tag tag : this.aggregatedWorkspace.getTags()) {
            this.tags.put(tag.getName(), tag);
        }
    }

    private void createReferenceMap() {
        for (Reference ref : this.aggregatedWorkspace.getReferences()) {
            this.currentReferences.put(this.getRefKey(ref), ref);
            this.referenceMap.put(ref.getId(), ref);
        }
    }


    private void updateCachedRefs(Reference ref) {
        this.currentReferences.put(this.getRefKey(ref), ref);
        this.referenceMap.put(ref.getId(), ref);
    }

    private void updateCachedComponents(Component c) {
        if (c.getId() != null) {
            this.currentComponents.put(c.getId(), c);
            this.componentTree.put(this.getPath(c), c);
        } else {
            throw new RuntimeException("Component " + c.getName() + " has no id, cannot cache it:\n" + c);
        }
    }

    private void createComponentTree() {
        for (Component c : this.aggregatedWorkspace.getComponents()) {
            this.currentComponents.put(c.getId(), c);
            if (c.getParent() == null) {
                this.componentTree.put(c.getName(), c);
            }
        }

        for (Component c : this.aggregatedWorkspace.getComponents()) {
            if (c.getParent() != null) {
                this.componentTree.put(this.getPath(c), c);
            }
        }
    }

    private String getPath(Component c) {
        String name = c.getName();
        Component parent = this.currentComponents.get(c.getParent());
        while (parent != null) {
            name = parent.getName() + "." + name;
            parent = this.currentComponents.get(parent.getParent());
        }
        return name;
    }

    /**
     * Gets an existing tag by name
     *
     * @param name Name of the tag
     * @return Returns the tag
     */
    public Tag getTagByName(String name) {
        Tag t = this.tags.get(name);
        if (null == t) {
            t = new Tag(name, this.workspace.getId(), "");
            this.tags.put(name, t);
        }
        return t;
    }

    public void updateTag(Tag tag) {
        this.tags.put(tag.getName(), tag);
        boolean found = false;
        for (Tag t : this.updatedTags) {
            if (t.getName().equals(tag.getName())) {
                found = true;
            }
        }

        if (!found) {
            this.updatedTags.add(tag);
        }
    }

    public void syncTags() {
        for (Tag t : this.updatedTags) {
            if (null == t.getId()) {
                Tag nt = tagService.createTag(t);
                this.tags.put(nt.getName(), nt);
            } else {
                tagService.updateTag(t.getId(), t);
                this.tags.put(t.getName(), t);
            }

        }
    }

    public Component getComponentByPath(String compPath) {
        return (Component) this.clone(this.componentTree.get(compPath));
    }

    public Component getComponentByPath(Component comp) {
        return (Component) this.clone(this.componentTree.get(this.getPath(comp)));
    }

    public BasicModel clone(BasicModel obj) {
        return (null != obj) ? gson.fromJson(gson.toJson(obj), obj.getClass()) : null;
    }

    public Component getComponentById(String id) {
        return this.currentComponents.get(id);
    }

    public Component addComponent(Component comp) {
        Component currentComp = this.getComponentByPath(comp);
        if (null == currentComp) {
            currentComp = this.componentService.createComponent(comp);
            this.newComponents.put(currentComp.getId(), currentComp);
            this.componentTree.put(getPath(currentComp), currentComp);
        } else if (this.isDifferent(currentComp, comp)) {
            this.updatedComponentCount++;
            comp.setCreated(null);
            currentComp = componentService.updateComponent(currentComp.getId(), comp);
        }
        this.modifiedList.add(currentComp);
        this.updateCachedComponents(currentComp);
        return currentComp;
    }

    private String getRefKey(Reference ref) {
        return ref.getSource() + ref.getType() + ref.getTarget();
    }

    public Reference getCurrentReference(Reference ref) {
        return this.currentReferences.get(this.getRefKey(ref));
    }

    public Reference getCurrentReferenceById(String id) {
        return this.referenceMap.get(id);
    }

    public Reference addReference(Reference ref) {
        Reference currentRef = getCurrentReference(ref);
        if (null == currentRef) {
            currentRef = referenceService.createReference(ref);
            this.newRefs.put(this.getRefKey(currentRef), currentRef);
        } else if (this.isDifferent(currentRef, ref)) {
            ref.setCreated(null);
            currentRef = referenceService.updateReference(currentRef.getId(), ref);
            this.updatedRefCount++;

        }
        this.modifiedList.add(currentRef);
        this.updateCachedRefs(ref);

        return currentRef;
    }

    /**
     * Returns an update report with statistics of created, updated and deleted references and components.
     *
     * @return a String with the report.
     */
    public String getReport() {
        return "Report\n - Created " + newComponents.size() + " components\n - Updated " + this.updatedComponentCount + " components\n - Created " + newRefs.size() + " references\n - Updated " + this.updatedRefCount + " references\n - DELETED " + this.deletedComponents + " components\n - DELETED " + this.deletedRefs + " references";
    }


    public void deleteNotSyncedItems() {
        this.deleteOldReferences(this.deleteOldComponents());
    }

    public Set<String> deleteOldReferences(Set<String> deletedComponents) {

        Set<String> refsToDelete = getObjectsToDelete(this.referenceMap.keySet());
        for (String id : refsToDelete) {
            Reference ref = this.referenceMap.get(id);
            if (id != null && id.trim().length() > 0 && ref != null && !deletedComponents.contains(ref.getSource()) && !deletedComponents.contains(ref.getTarget())) {
                try {
                    this.referenceService.deleteReference(id);
                } catch (Exception ite) {
                    System.out.println("Reference with id: " + id + " was already deleted.");
                }
                this.deletedRefs++;
            }
        }
        return refsToDelete;
    }

    public Set<String> deleteOldComponents() {
        Set<String> componentsToDelete = getObjectsToDelete(this.currentComponents.keySet());
        for (String id : componentsToDelete) {
            deletedComponents++;
            try {
                this.componentService.deleteComponent(id);
            } catch (Exception ite) {
                System.out.println("Component with id: " + id + " was already deleted.");
            }
        }
        return componentsToDelete;
    }

    private Set<String> getObjectsToDelete(Set<String> objectIdsToDelete) {
        Set<String> componentsToDelete = objectIdsToDelete;
        for (BasicModel model : this.modifiedList) {
            componentsToDelete.remove(model.getId());
        }
        return componentsToDelete;
    }

    public Workspace updateWorkspaceIfDifferent(Workspace newWorkspace) {
        Workspace existingWorkspace = this.workspaceService.getWorkspaceById(this.workspace.getId());
        if (this.isDifferent(existingWorkspace, newWorkspace)) {
            this.workspace = this.workspaceService.updateWorkspace(this.workspace.getId(), newWorkspace);
        }
        return this.workspace;
    }

    public Workspace getWorkspace() {
        return this.workspace;
    }

    public Model getModel() {
        return this.model;
    }

    public AggregatedWorkspace getAggregatedWorkspace() {
        return this.aggregatedWorkspace;
    }

    private boolean isDifferent(BasicModel current, BasicModel newComp) {
        boolean isDifferent = false;
        for (java.lang.reflect.Field f : newComp.getClass().getDeclaredFields()) {
            try {
                f.setAccessible(true);
                Object newValue = f.get(newComp);
                Object oldValue = f.get(current);
                if (newValue != null) {
                    if (newValue.getClass().getSimpleName().indexOf("Map") > -1) {
                        Map newVal = (Map) newValue;
                        Map oldVal = (Map) oldValue;
                        boolean mapDifferent = addIfFoundAndCheckDifferent(newVal, oldVal);
                        isDifferent = isDifferent || mapDifferent;
                    } else if (!newValue.equals(oldValue)) {
                        isDifferent = true;
                    }
                } else if (oldValue != null) {
                    f.set(newComp, oldValue);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return isDifferent;
    }

    private boolean addIfFoundAndCheckDifferent(Map newMap, Map oldMap) {
        boolean isDifferent = false;
        for (Object key : oldMap.keySet()) {
            Object val = newMap.get(key);
            Object oldVal = oldMap.get(key);
            //TODO: Check arrayt list v.s. gson array
            if (val != null && oldVal != null && !oldVal.equals(val)) {
                isDifferent = true;
            } else if (null != oldVal && val == null) {
                newMap.put(key, oldVal);
            }
        }
        for (Object key : newMap.keySet()) {
            if (!oldMap.containsKey(key)) {
                isDifferent = true;
            }
        }
        return isDifferent;
    }


}
