package dev.monczall.atiperatask.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GitHubResponseBranchDto {
    private String name;
    private String lastCommitSha;
}
