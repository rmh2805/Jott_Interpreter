import os
import shutil

from os.path import isfile, join

input_dir = "./samples/in/"
output_dir = "./samples/out/"

input_files = [f for f in os.listdir(input_dir) if isfile(join(input_dir, f))]

tmp_path = "./tmp/"
tmp_out_path = tmp_path + "out/"
tmp_diff_path = tmp_path + "diff/"

try:
    shutil.rmtree(tmp_path)
except:
    # do not delete non existent directory
    pass

os.mkdir(tmp_path)
os.mkdir(tmp_out_path)
os.mkdir(tmp_diff_path)

cmd = "java Jott "
diff_args = "--suppress-common-lines --ignore-all-space "

print("Testing against all files in: " + input_dir)
print("############################################")
print()

tests = len(input_files)
passes = 0

for file in input_files:
    root = file[:-2]

    # file paths
    out = tmp_out_path + root
    cmp = output_dir + root + ".out"
    diff_path = tmp_diff_path + root + ".diff"

    # run program then diff output
    os.system(cmd + input_dir + file + " > " + out + " 2>&1")
    os.system("diff " + diff_args +  out + " " + cmp + " > " + diff_path + " 2>&1")

    file = open(diff_path)
    size = os.stat(diff_path).st_size

    # if size is zero, there is no diff
    if size == 0:
        file.close()
        os.remove(diff_path)
        passes += 1
    else:
        print("failed....................... \t", root)

print("Passed " + str(passes) + "/" + str(tests) + " tests.")

print()
print("############################################")
print("Testing has concluded. Please check ./tmp/diff/ for any failed tests.")
