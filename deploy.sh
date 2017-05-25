#!/bin/bash
# Check if the branch is master
# if so it will generate the documentation
# else it will just run test to verify build

set -ev # -e tells the script to stop if error, -v prints the script before running

if [ $TRAVIS_BRANCH == 'master' -a $TRAVIS_PULL_REQUEST == 'false' ]; then
	mvn clean verify site -X;
else
	mvn verify;
fi