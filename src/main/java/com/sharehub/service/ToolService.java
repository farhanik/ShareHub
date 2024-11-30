package com.sharehub.service;

import com.sharehub.model.Tool;
import java.util.List;
import java.util.Optional;

public interface ToolService {
    Tool addTool(Tool tool);
    Optional<Tool> getToolById(Long id);
    List<Tool> getToolsByOwner(Long ownerId);
    List<Tool> getAvailableTools();
    Tool updateTool(Tool tool);
    void deleteTool(Long id);
    List<Tool> searchTools(String keyword);
    List<Tool> getToolsByCategory(String category);
}