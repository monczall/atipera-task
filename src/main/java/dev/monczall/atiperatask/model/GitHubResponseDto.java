package dev.monczall.atiperatask.model;

import java.util.List;

public record GitHubResponseDto(String repositoryName, String ownerLogin, List<GitHubResponseBranchDto> branchList) {
}
