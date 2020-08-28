import sys
import re

def parse_stdin():
    finding_candidates = True
    count = 0
    men = {}           # Pursuers attempting to match with their preferences
    women = {}           # Pursued settling for an approaching candidate
    preferences = {}        # The preferences for all candidates
    candidate_count = 0
    ranking = {}            # A rank[settler][man] instant lookup for women' preference

    for line in sys.stdin:
        line = line.strip()
        
        #Comments are unecessary
        if (line.startswith('#')):
            continue
        
        #Number of matches, we don't use this
        if '=' in line:
            count = int(line[-1])
            continue

        #Start finding candidates
        if (line == ''): 
            finding_candidates = False

        candidate_match = re.findall('(\d+) (.*)', line)
        if candidate_match and finding_candidates:
            index = candidate_match[0][0]
            name = candidate_match[0][1]
            if candidate_count % 2 == 0: 
                men[index] = name
            else:
                women[index] = name
            candidate_count += 1

        preference_match = re.findall('(\d+): (.*)', line)
        if preference_match: 
            prefs = preference_match[0][1].split(' ')
            preferences[preference_match[0][0]] = prefs
            if preference_match[0][0] in women:
                ranking[preference_match[0][0]] = {}
                for index, preference in enumerate(prefs):
                    ranking[preference_match[0][0]][preference] = index

    return men, women, preferences, ranking


def stable_match(men, women, preferences, ranking):
    matched_men = {}
    matched_women = {}
    not_matched = set(men.keys())

    while not_matched:
        man = not_matched.pop()
        if preferences[man]:
            # Use preference stack for getting man preferences in order (constant time)
            woman = preferences[man].pop(0) 

            if woman not in matched_women: 
                matched_men[man] = woman
                matched_women[woman] = man
            else: 
                settled_with = matched_women[woman]
                # Use preference lookup table for getting settler preferences (constant time)
                likes_current_more = ranking[woman][settled_with] < ranking[woman][man]
                if (likes_current_more):
                    not_matched.add(man)
                else: 
                    matched_men[man] = woman
                    matched_women[woman] = man
                    not_matched.add(settled_with)
    
    result = list(matched_men.items())
    return result


def print_result(results, men, women):
    for result in results: 
        to_print = f"{men[result[0]]} -- {women[result[1]]}"
        print(to_print)

men, women, preferences, ranking = parse_stdin()
result = stable_match(men, women, preferences, ranking)
print_result(result, men, women)
