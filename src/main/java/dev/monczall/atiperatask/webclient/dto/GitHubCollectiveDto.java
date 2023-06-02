package dev.monczall.atiperatask.webclient.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class GitHubCollectiveDto {
    private String name;
    private boolean fork;
    private OwnerDto owner;
    private List<GitHubBranchDto> branchList;

    public void setBranchList(List<GitHubBranchDto> branchList) {
        this.branchList = branchList;
    }
}
