import sklearn.preprocessing as sk
import pandas as pd

filename = "2016_New_Coder_Survey_NNRO.csv"
label = "Gender"
instances = ["Age", "Income", "IsEthnicMinority", "IsSoftwareDev"]

row_vector = instances[:].append(label)
print(row_vector)
print(instances)

file = pd.read_csv(filename, usecols=row_vector)

for field in instances:
    file[field] = sk.normalize(file[field])[0]

file.to_csv("2016_New_Coder_Survey_NNRO_Normalized.csv", index=False, header=False)
