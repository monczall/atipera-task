package dev.monczall.atiperatask.service;

import dev.monczall.atiperatask.model.GitHubResponseBranchDto;
import dev.monczall.atiperatask.model.GitHubResponseDto;
import dev.monczall.atiperatask.webclient.GitHubClient;
import dev.monczall.atiperatask.webclient.dto.GitHubCollectiveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class GitHubService {

    private final GitHubClient gitHubClient;

    public List<GitHubResponseDto> getUserReposAndBranches(String userName) {
        List<GitHubCollectiveDto> userRepos = gitHubClient.getReposForUser(userName);

        List<GitHubCollectiveDto> filteredUserRepos = userRepos.stream()
                .filter(repo -> !repo.isFork())
                .collect(Collectors.toList());

        filteredUserRepos.stream()
                .forEach(repo -> {
                    repo.setBranchList(gitHubClient.getReposBranches(userName, repo.getName()));
                });

        List<GitHubResponseDto> responseDtoList = new ArrayList<>();

        filteredUserRepos.stream()
                .forEach(repo -> {
                    GitHubResponseDto responseDto = new GitHubResponseDto(
                            repo.getName(),
                            repo.getOwner().login(),
                            repo.getBranchList().stream()
                                    .map(branch -> new GitHubResponseBranchDto(branch.name(), branch.commit().sha()))
                                    .toList()
                            );

                    responseDtoList.add(responseDto);
                });


        return responseDtoList;
    }

}
