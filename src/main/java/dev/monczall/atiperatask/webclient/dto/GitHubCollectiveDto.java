package dev.monczall.atiperatask.webclient.dto;

import java.util.List;

public record GitHubCollectiveDto(String name, boolean fork, OwnerDto owner, List<GitHubBranchDto> branchList) {
}
