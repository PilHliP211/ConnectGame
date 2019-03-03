 #!/bin/bash

# Check whether bin and source directories exist.
# If directories exist, remove it so that new compile command 
# doesn't have issues.
if test -d ./bin
then
   rm -r ./bin
   echo -e "\nOld bin files removed"
fi

if test -d ./docs
then 
    rm -r ./docs
    echo -e "\nOld docs files removed\n"
fi
