package com.ardoq.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.ardoq.model.Model;

import retrofit.mime.TypedString;

/**
 * SimpleModelService let's you retrieve Models.
 *
 * @see com.ardoq.service.DeprecatedModelService
 * @see com.ardoq.model.Model
 * <p>
 * **A word of caution, the model API and logic is under upgrade and will be updated in the future.**
 * </p>
 */
public class SimpleModelService {
    private final ModelService modelService;

    public SimpleModelService(ModelService modelService) {
        this.modelService = modelService;
    }

    public List<Model> getAllModels() {
        return modelService.getAllModels();
    }

    public List<Model> getAllTemplate(boolean includeCommon) {
        List<Model> allModels = modelService.getAllModelsIncludingCommonTemplate();
        ArrayList<Model> templates = new ArrayList<Model>();
        for (Model model : allModels) {
            if (model.isTemplate()) {
                if (!includeCommon && !model.isTemplate()) {
                    templates.add(model);
                } else {
                    templates.add(model);
                }
            }
        }
        return templates;
    }

    public Model getModelById(String id) {
        return modelService.getModelById(id);
    }

    public Model getTemplateById(String id) {
        List<Model> allModels = modelService.getAllModelsIncludingCommonTemplate();
        for (Model model : allModels) {
            if (model.getId().equals(id)) {
                return model;
            }
        }
        return null;
    }

    public Model getTemplateByName(String name) {
        List<Model> allModels = modelService.getAllModelsIncludingCommonTemplate();
        List<Model> result = new ArrayList<Model>();
        for (Model m : allModels) {
            if (m.getName().equals(name)) {
                result.add(m);
            }
        }

        if (result.isEmpty()) {
            throw new IllegalArgumentException("No model with the name " + name + " exist!");
        } else if (result.size() > 1) {
            throw new IllegalArgumentException("Multiple models that match the name " + name + " exist!");
        } else {
            return result.get(0);
        }
    }


    /**
     * Tries to lookup the model by name, and returns it if it's found.
     * If not, it is created from the provided JSON input stream.
     *
     * @param name
     * @param modelJson
     * @return model
     * @throws IOException
     */
    public Model findOrCreateTemplate(String name, InputStream modelJson) throws IOException {
        try {
            Model m = getTemplateByName(name);
            return m;
        } catch (IllegalArgumentException ignore) {
        }

        String modelStr = IOUtils.toString(modelJson, "UTF-8");
        return modelService.createModel(new TypedString(modelStr));
    }


}
