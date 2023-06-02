package dev.monczall.atiperatask.webclient.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class GitHubBranchDto {
    private String name;
    private GitHubBranchCommitDto commit;
}
