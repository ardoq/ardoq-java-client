package com.ardoq.service;

import com.ardoq.model.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * SimpleModelService let's you retrieve Models.
 * @see com.ardoq.service.DeprecatedModelService
 *
 * @see com.ardoq.model.Model
 *
 * **A word of caution, the model API and logic is under upgrade and will be updated in the future.**
 */
public class SimpleModelService implements ModelService {
    private final DeprecatedModelService deprecatedModelService;

    public SimpleModelService(DeprecatedModelService deprecatedModelService) {
        this.deprecatedModelService = deprecatedModelService;
    }

    @Override
    public List<Model> getAllModels() {
        return deprecatedModelService.getAllModels();
    }

    @Override
    public Model getModelById(String id) {
        return deprecatedModelService.getModelById(id);
    }

    @Override
    public Model getModelByName(String name) {
        List<Model> allModels = deprecatedModelService.getAllModels();
        List<Model> result = new ArrayList<Model>();
        for (Model m : allModels) {
            if (m.getName().equals(name)) {
                result.add(m);
            }
        }

        if (result.size() > 1 || result.isEmpty()) {
            throw new IllegalArgumentException("No unique workspace with that name exist!");
        } else {
            return result.get(0);
        }
    }

}
