#! /bin/bash

function git_pull_all() {
        for dir_name in `ls $1`
        do
                cur_dir="$1/$dir_name"
                # echo $cur_dir
                if [ -d $cur_dir ]
                then
                        echo " -----------------------------------------------"
                        echo " --- $cur_dir enter ---"
                        echo "[01] : cd  $cur_dir"
                        cd $cur_dir
                        echo "[02] : git pull"
                        git pull
                        echo "[03] git fetch"
                        git fetch
                        echo " --- $cur_dir exit --- "
                fi
        done
}
echo " current directory : $PWD "
echo " --- start git_pull_all --- "
git_pull_all $PWD
echo " --- end git_pull_all --- "
