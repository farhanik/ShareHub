package com.sharehub.controller;

import com.sharehub.model.Tool;
import com.sharehub.service.ToolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tools")
@CrossOrigin(origins = "*")
public class ToolController
{
    private final ToolService toolService;

    public ToolController(ToolService toolService) {
        this.toolService = toolService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Tool>> addTool(@RequestBody Tool tool)
    {
        System.out.println("Received Tool: " + tool);
        Tool addedTool = toolService.addTool(tool);
        return ResponseEntity.ok(new ApiResponse<>(true, "Tool added successfully", addedTool));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Tool>> getTool(@PathVariable Long id)
    {
        return toolService.getToolById(id)
                .map(tool -> ResponseEntity.ok(new ApiResponse<>(true, "Tool found", tool)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/owner/{ownerId}")
    // Retrieves all tools owned by a specific user
    public ResponseEntity<ApiResponse<List<Tool>>> getToolsByOwner(@PathVariable Long ownerId)
    {
        List<Tool> tools = toolService.getToolsByOwner(ownerId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Tools retrieved successfully", tools));
    }

    @GetMapping("/available")
    //Retrieves all tools marked as available
    public ResponseEntity<ApiResponse<List<Tool>>> getAvailableTools()
    {
        List<Tool> tools = toolService.getAvailableTools();
        return ResponseEntity.ok(new ApiResponse<>(true, "Available tools retrieved", tools));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Tool>>> searchTools(@RequestParam String keyword)
    {
        List<Tool> tools = toolService.searchTools(keyword);
        return ResponseEntity.ok(new ApiResponse<>(true, "Search results", tools));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Tool>> updateTool(@PathVariable Long id, @RequestBody Tool tool)
    {
        try {
            tool.setId(id);
            Tool updatedTool = toolService.updateTool(tool);
            return ResponseEntity.ok(new ApiResponse<>(true, "Tool updated successfully", updatedTool));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTool(@PathVariable Long id)
    {
        toolService.deleteTool(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Tool deleted successfully", null));
    }
}