# Read the Location History

import json

json_file = "/Users/apple/Documents/study/google_data/Google Maps/Resources/sample.json"

with open(json_file) as f:
    data = json.load(f)

# Output: {'name': 'Bob', 'languages': ['English', 'Fench']}
print(data)
