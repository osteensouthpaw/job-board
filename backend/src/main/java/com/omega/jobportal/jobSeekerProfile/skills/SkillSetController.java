package com.omega.jobportal.jobSeekerProfile.skills;

import com.omega.jobportal.jobSeekerProfile.data.SkillSetRequest;
import com.omega.jobportal.jobSeekerProfile.data.SkillSetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/job-seekers/{jobSeekerId}/skills")
public class SkillSetController {
    private final SkillSetService skillSetService;

    @GetMapping
    public ResponseEntity<List<SkillSetResponse>> findSkillsByJobSeekerId(@PathVariable Long jobSeekerId) {
        List<SkillSetResponse> skills = skillSetService.findSkillsByJobseekerId(jobSeekerId);
        return ResponseEntity.ok(skills);
    }

    @GetMapping("{skillId}")
    public ResponseEntity<SkillSetResponse> findSkillById(@PathVariable Long jobSeekerId, @PathVariable Long skillId) {
        SkillSetResponse skillSetResponse = skillSetService.findSkillById(jobSeekerId, skillId);
        return ResponseEntity.ok(skillSetResponse);
    }

    @PostMapping
    public ResponseEntity<SkillSetResponse> saveSkillSet(@RequestBody SkillSetRequest request) {
        SkillSetResponse skillSetResponse = skillSetService.saveSkillSet(request);
        return ResponseEntity.ok(skillSetResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SkillSetResponse> updateSkillSet(@RequestBody SkillSetRequest request, @PathVariable Long id) {
        SkillSetResponse skillSetResponse = skillSetService.updateSkillSet(request, id);
        return ResponseEntity.ok(skillSetResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSkillSet(@PathVariable Long id) {
        skillSetService.deleteSkillSet(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
