import sklearn.preprocessing as sk
import pandas as pd

filename = "googlePlayStoreRefined.csv"
label = "Rating"
instances = ["Artanddesign", "AUTO_AND_VEHICLES", "BEAUTY",
    "BOOKS_AND_REFERENCE", "BUSINESS", "COMICS", "COMMUNICATION",
    "DATING", "EDUCATION", "ENTERTAINMENT", "EVENTS", "FINANCE",
    "FOOD_AND_DRINK", "HEALTH_AND_FITNESS", "HOUSE_AND_HOME",
    "LIBRARIES_AND_DEMO", "LIFESTYLE", "GAME", "FAMILY", "MEDICAL",
    "SOCIAL", "SHOPPING", "PHOTOGRAPHY", "SPORTS", "TRAVEL_AND_LOCAL",
    "TOOLS", "PERSONALIZATION", "PRODUCTIVITY", "PARENTING", "WEATHER",
    "VIDEO_PLAYERS", "NEWS_AND_MAGAZINES", "MAPS_AND_NAVIGATION",
    "Reviews", "Size", "Installs", "Price"]

row_vector = instances[:].append(label)
print(row_vector)
print(instances)

file = pd.read_csv(filename, usecols=row_vector)

for field in instances:
    print(field)
    print(file[field])
    file[field] = sk.normalize(file[field])[0]

file.to_csv("googlePlayStoreRefined_Normalized.csv", index=False, header=False)
