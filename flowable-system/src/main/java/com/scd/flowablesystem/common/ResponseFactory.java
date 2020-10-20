package com.scd.flowablesystem.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.flowable.engine.FormService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.IdentityService;
import org.flowable.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author scd
 * @since
 */
@Component
public class ResponseFactory {
    private final IdentityService identityService;
    private final FormService formService;
    private final HistoryService historyService;
    private final ObjectMapper objectMapper;

    @Autowired
    public ResponseFactory(IdentityService identityService, FormService formService, HistoryService historyService, ObjectMapper objectMapper) {
        this.identityService = identityService;
        this.formService = formService;
        this.historyService = historyService;
        this.objectMapper = objectMapper;
    }

    public List<ModelResponse> createModelResponseList(List<Model> models) {
        List<ModelResponse> responseList = new ArrayList<>();
        for (Model instance : models) {
            responseList.add(createModelResponse(instance));
        }
        return responseList;
    }

    public ModelResponse createModelResponse(Model model) {
        ModelResponse response = new ModelResponse();
        response.setCategory(model.getCategory());
        response.setCreateTime(model.getCreateTime());
        response.setId(model.getId());
        response.setKey(model.getKey());
        response.setLastUpdateTime(model.getLastUpdateTime());
        try {
            JsonNode modelNode = objectMapper.readTree(model.getMetaInfo());
            response.setDescription(modelNode.get("description").asText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setName(model.getName());
        response.setVersion(model.getVersion());
        if (model.getDeploymentId() != null) {
            response.setDeployed(true);
        } else {
            response.setDeployed(false);
        }
        response.setTenantId(model.getTenantId());
        return response;
    }

}
