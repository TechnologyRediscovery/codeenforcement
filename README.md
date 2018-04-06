# turtle-creek-code-enforcement
Code Enforcement and occupancy permitting database system for the Turtle Creek Valley Council of Governments in the Greater Pittsburgh area
## Overall project philosphy

## Postgres setup

Run these commands once in the postgres user account

CREATE USER xxxxx WITH PASSWORD 'xxxxxx';

CREATE DATABASE xxxx ENCODING 'UTF8'  LC_COLLATE = 'en_US.UTF-8' LC_CTYPE = 'en_US.UTF-8';

GRANT ALL PRIVILEGES ON DATABASE xxx to xxx;


## Git reference
/codeenforcement git status
	- up to date w/ origin/master
git add * 
	- add all file changes
git log 
	- shows all of previous commits
git commit
	- prompted for git user info 
git remote -v
	-push origin master sends to eric
1) git branch [branchname]
	- create new branch
2) git checkout [branchName]
	- move to [branchName] 
3) edit code/changes
4) git add *  new 
	- stage changes for push
5) git commit -m "message"
6) git push origin [branchName]
7) fulfill pull request on github.com
