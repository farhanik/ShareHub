package com.sharehub.service.impl;

import com.sharehub.model.Tool;
import com.sharehub.service.ToolService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ToolServiceImpl implements ToolService {
    private Map<Long, Tool> toolDatabase = new HashMap<>();
    private Long currentId = 1L;

    @Override
    public Tool addTool(Tool tool)
    {
        tool.setId(currentId++);
        toolDatabase.put(tool.getId(), tool);
        return tool;
    }

    @Override
    public Optional<Tool> getToolById(Long id) {
        return Optional.ofNullable(toolDatabase.get(id));
    }

    @Override
    public List<Tool> getToolsByOwner(Long ownerId) {
        return toolDatabase.values().stream()
                .filter(tool -> tool.getOwner().getId().equals(ownerId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Tool> getAvailableTools()
    {
        return toolDatabase.values().stream()
                .filter(Tool::isAvailable)
                .collect(Collectors.toList());
    }

    @Override
    public Tool updateTool(Tool tool)
    {
        if (!toolDatabase.containsKey(tool.getId()))
        {
            throw new IllegalArgumentException("Tool not found");
        }
        toolDatabase.put(tool.getId(), tool);
        return tool;
    }

    @Override
    public void deleteTool(Long id) {
        toolDatabase.remove(id);
    }

    @Override
    //Showing all the tools by searched
    public List<Tool> searchTools(String keyword)
    {
        String lowercaseKeyword = keyword.toLowerCase();
        return toolDatabase.values().stream()
                .filter(tool ->
                        tool.getName().toLowerCase().contains(lowercaseKeyword) ||
                                tool.getDescription().toLowerCase().contains(lowercaseKeyword))
                .collect(Collectors.toList());
    }

    @Override
    public List<Tool> getToolsByCategory(String category)
    {
        return toolDatabase.values().stream()
                .filter(tool -> tool.getCategory().equals(category))
                .collect(Collectors.toList());
    }
}