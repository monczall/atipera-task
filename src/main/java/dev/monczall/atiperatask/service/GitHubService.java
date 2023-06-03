package dev.monczall.atiperatask.service;

import dev.monczall.atiperatask.model.GitHubResponseBranchDto;
import dev.monczall.atiperatask.model.GitHubResponseDto;
import dev.monczall.atiperatask.webclient.GitHubClient;
import dev.monczall.atiperatask.webclient.dto.GitHubBranchDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GitHubService {

    private final GitHubClient gitHubClient;

    public List<GitHubResponseDto> getUserReposAndBranches(String userName) {
        List<GitHubResponseDto> responseDtoList = gitHubClient.getReposForUser(userName).stream()
                .filter(repo -> !repo.fork())
                .map(repo -> {
                    List<GitHubBranchDto> branchList = gitHubClient.getReposBranches(userName, repo.name());
                    List<GitHubResponseBranchDto> responseBranchList = branchList.stream()
                            .map(branch -> new GitHubResponseBranchDto(branch.name(), branch.commit().sha()))
                            .toList();
                    return new GitHubResponseDto(repo.name(), repo.owner().login(), responseBranchList);
                })
                .toList();

        return responseDtoList;
    }

}
