package dev.monczall.atiperatask.service;

import dev.monczall.atiperatask.model.GitHubResponseDto;
import dev.monczall.atiperatask.webclient.GitHubClient;
import dev.monczall.atiperatask.webclient.dto.GitHubBranchCommitDto;
import dev.monczall.atiperatask.webclient.dto.GitHubBranchDto;
import dev.monczall.atiperatask.webclient.dto.GitHubCollectiveDto;
import dev.monczall.atiperatask.webclient.dto.OwnerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GitHubServiceTest {

    @Mock
    private GitHubClient gitHubClient;

    private GitHubService gitHubService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gitHubService = new GitHubService(gitHubClient);
    }

    @Test
    void GitHubService_GetUserReposAndBranches() {
        // Arrange
        String userName = "testuser";

        OwnerDto owner = new OwnerDto("owner1");
        List<GitHubCollectiveDto> repos = Arrays.asList(
                new GitHubCollectiveDto("repo1", false, owner, null),
                new GitHubCollectiveDto("repo2", true, owner, null),
                new GitHubCollectiveDto("repo3", false, owner, null)
        );

        GitHubBranchCommitDto branchCommit1 = new GitHubBranchCommitDto("sha1");
        GitHubBranchCommitDto branchCommit2 = new GitHubBranchCommitDto("sha2");
        GitHubBranchCommitDto branchCommit3 = new GitHubBranchCommitDto("sha3");
        GitHubBranchCommitDto branchCommit4 = new GitHubBranchCommitDto("sha4");
        List<GitHubBranchDto> branches1 = Arrays.asList(
                new GitHubBranchDto("branch1", branchCommit1),
                new GitHubBranchDto("branch2", branchCommit2)
        );
        List<GitHubBranchDto> branches2 = Arrays.asList(
                new GitHubBranchDto("branch3", branchCommit3),
                new GitHubBranchDto("branch4", branchCommit4)
        );

        when(gitHubClient.getReposForUser(userName)).thenReturn(repos);
        when(gitHubClient.getReposBranches(userName, "repo1")).thenReturn(branches1);
        when(gitHubClient.getReposBranches(userName, "repo3")).thenReturn(branches2);

        // Act
        List<GitHubResponseDto> responseDtoList = gitHubService.getUserReposAndBranches(userName);
        GitHubResponseDto response1 = responseDtoList.get(0);
        GitHubResponseDto response2 = responseDtoList.get(1);

        // Assert
        assertEquals(2, responseDtoList.size());

        assertEquals("repo1", response1.repositoryName());
        assertEquals("owner1", response1.ownerLogin());
        assertEquals("branch1", response1.branchList().get(0).name());
        assertEquals("sha1", response1.branchList().get(0).lastCommitSha());
        assertEquals("branch2", response1.branchList().get(1).name());
        assertEquals("sha2", response1.branchList().get(1).lastCommitSha());

        assertEquals("repo3", response2.repositoryName());
        assertEquals("owner1", response2.ownerLogin());
        assertEquals("branch3", response2.branchList().get(0).name());
        assertEquals("sha3", response2.branchList().get(0).lastCommitSha());
        assertEquals("branch4", response2.branchList().get(1).name());
        assertEquals("sha4", response2.branchList().get(1).lastCommitSha());

    }

}