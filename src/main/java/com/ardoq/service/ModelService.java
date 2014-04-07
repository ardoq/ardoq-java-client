package com.ardoq.service;

import com.ardoq.model.Model;

import java.util.List;

public interface ModelService {
    List<Model> getAllModels();

    Model getModelById(String id);

    Model getModelByName(String name);
}
