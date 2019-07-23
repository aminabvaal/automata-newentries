git add .
git commit -a -m "commit"
git -c credential.helper= -c core.quotepath=false -c log.showSignature=false merge origin/master --no-stat -v
