package com.omega.jobportal.jobSeekerProfile.skills;

import com.omega.jobportal.jobSeekerProfile.data.SkillSetRequest;
import com.omega.jobportal.jobSeekerProfile.data.SkillSetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/job-seeker/skills")
public class SkillSetController {
    private final SkillSetService skillSetService;

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
}
