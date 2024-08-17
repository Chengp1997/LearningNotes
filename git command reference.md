# Git command quick reference notes

### Basic

`git status` 

`git add -A`

`git commit -m "xxxx"`

`git push`

### GitIgnore

remove cached in repository

`git rm --cached .DS_Store`

find all if already commit

`find . -name .DS_Store -print0 | xargs -0 git rm --ignore-unmatch`

For all repo

`git config --global core.excludesfile ~/.gitignore_global`

gitIgnore file - apply to all

`**/.DS_Store`

`*.xxx`

### Git submodule

we can add other repos as submodule in one repo

`git submodule add <URL>/repoName repoName`

it will create a submodule file .gitmodules - point to current version

Newer versions of Git will do this automatically, but older versions will require you to explicitly tell Git to download the contents of `repoName`:

`git submodule update --init --recursive`

### Rebase

-i interactive

`git rebase`

