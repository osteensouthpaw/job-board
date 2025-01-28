package com.omega.jobportal.jobSeekerProfile.skills;

import com.omega.jobportal.jobSeekerProfile.data.SkillSetRequest;
import com.omega.jobportal.jobSeekerProfile.data.SkillSetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
