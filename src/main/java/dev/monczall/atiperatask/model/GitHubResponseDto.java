package dev.monczall.atiperatask.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GitHubResponseDto {
    private String repositoryName;
    private String ownerLogin;
    private List<GitHubResponseBranchDto> branchList;
}
