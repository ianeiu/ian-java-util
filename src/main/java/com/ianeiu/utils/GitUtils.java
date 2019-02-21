package com.ianeiu.utils;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

/**
 * @Description: 需创建远程仓库，本地仓库
 * @author: wm
 * @date: 2018年8月17日 下午3:58:24
 * @version: 1.0
 */
public class GitUtils {
	/**
	 * 远程仓库地址
	 */
    private static String remotePath;
    /**
     * 本地仓库地址
     */
    private static String localPath;
    /**
     * 远程仓库用户名
     */
    private static String userName;
    /**
     * 远程仓库密码
     */
    private static String password;
    
	//@Value("${git.remotePath}")
	public void setRemotePath(String remotePath) {
		GitUtils.remotePath = remotePath;
	}
    //@Value("${git.localPath}")
	public void setLocalPath(String localPath) {
		GitUtils.localPath = localPath;
	}
    //@Value("${git.userName}")
	public void setUserName(String userName) {
		GitUtils.userName = userName;
	}
    //@Value("${git.password}")
	public void setPassword(String password) {
		GitUtils.password = password;
	}



    /**
     * @Description: 克隆远程库
     * @author: wm
     * @date: 2018年8月17日 上午11:55:09
     * @version: 1.0
     * @return
     * @throws IOException
     * @throws GitAPIException
     */
    public static String cloneFromRemote() throws IOException, GitAPIException {
        //设置远程服务器上的用户名和密码
        UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider =new
                UsernamePasswordCredentialsProvider(userName,password);

        //克隆代码库命令
        CloneCommand cloneCommand = Git.cloneRepository();
        //设置远程URI
        Git git= cloneCommand.setURI(remotePath)
                //设置clone下来的分支
                .setBranch("master")
                //设置下载存放路径
                .setDirectory(new File(localPath))
                //设置权限验证
                .setCredentialsProvider(usernamePasswordCredentialsProvider)
                .call();
        
        return git.tag().toString(); 
    }
    
    /**
     * @Description: 本地仓库新增文件
     * @author: wm
     * @date: 2018年8月17日 下午4:04:47
     * @version: 1.0
     * @param filePattern localPath下上传文件路径
     * @throws IOException
     * @throws GitAPIException
     */
    public static void gitAddFile(String filePattern) throws IOException, GitAPIException {
        //git仓库地址
        Git git = new Git(new FileRepository(localPath+"/.git"));
        //添加文件
        git.add().addFilepattern(filePattern).call();
    }
    
    /**
     * @Description: 
     * @author: wm
     * @date: 2018年8月20日 下午4:04:25
     * @version: 1.0
     * @throws IOException
     * @throws GitAPIException
     */
    public static void gitDelFile() throws IOException, GitAPIException{
    	Git git = new Git(new FileRepository(localPath + "/.git"));
    	git.add().setUpdate(true).addFilepattern(".").call();
	}
    
    /**
     * @Description: 本地提交代码
     * @author: wm
     * @date: 2018年8月17日 下午4:07:08
     * @version: 1.0
     * @param commitMessage 提交的注释
     * @throws IOException
     * @throws GitAPIException
     * @throws JGitInternalException
     */
    public static void gitCommit(String commitMessage) throws IOException, GitAPIException,
	    JGitInternalException {
		//git仓库地址
		Git git = new Git(new FileRepository(localPath+"/.git"));
		//提交代码
		git.commit().setMessage(commitMessage).call();
	}
    
    /**
     * @Description: 拉取远程仓库内容到本地
     * @author: wm
     * @date: 2018年8月17日 下午4:11:18
     * @version: 1.0
     * @throws IOException
     * @throws GitAPIException
     */
    public static void gitPull() throws IOException, GitAPIException {
        UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider =new
		UsernamePasswordCredentialsProvider(userName, password);
		// git仓库地址
		Git git = new Git(new FileRepository(localPath + "/.git"));
		git.pull().setRemoteBranchName("master").setCredentialsProvider(usernamePasswordCredentialsProvider).call();
    }
    
    /**
     * @Description: push本地代码到远程仓库地址
     * @author: wm
     * @date: 2018年8月17日 下午4:13:00
     * @version: 1.0
     * @throws IOException
     * @throws JGitInternalException
     * @throws GitAPIException
     */
    public static void gitPush() throws IOException, JGitInternalException, GitAPIException {
		UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider =new
		UsernamePasswordCredentialsProvider(userName, password);
		// git仓库地址
		Git git = new Git(new FileRepository(localPath + "/.git"));
		git.push().setRemote("origin").setCredentialsProvider(usernamePasswordCredentialsProvider).call();
	}
}
