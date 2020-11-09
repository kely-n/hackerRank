from itertools import permutations
#This problem was asked by Snapchat.

#Given a string of digits, generate all possible valid IP address combinations.

#IP addresses must follow the format A.B.C.D, where A, B, C, and D are numbers between 0 and 255. Zero-prefixed numbers, such as 01 and 065, are not allowed, except for 0 itself.

#For example, given "2542540123", you should return ['254.25.40.123', '254.254.0.123'].

# generate all possible permutations
def generate_permutations(possible_digits):
    perm = permutations(possible_digits, 4)
    # remove duplicates
    perm = list(perm)
    myperm = [i for n, i in enumerate(perm) if i not in perm[:n]]
    return myperm


# generate possible count combinations, i.e. 3333 or 3332 or 3331
# get length of numbers, find the quotient and the remainder
def get_possible_count_combinations(length):
    # remainder
    r = length % 3
    # quotient
    num = (length - r) / 3
    combination = [0, 0, 0, 0]
    for i in range(num):
        combination[i] = 3
    if length < 12:
        combination[num] = r
    # print(combination)
    return combination


# generating valid combinations
# should not have zeros
def get_valid_combinations_without_0(combination):
    count = 3
    while count >= 0:
        if combination[count] == 0:
            i = count - 1
            while i >= 0:
                if combination[i] > 1:
                    combination[i] = combination[i] - 1
                    combination[count] = combination[count] + 1
                    break
                i -= 1
        count -= 1

    return combination


# generating valid combinations
# should not have ones if possibe
def get_valid_combinations_without_1(combination):
    count = 3
    while count >= 0:
        if combination[count] == 1:
            i = count - 1
            while i >= 0:
                if combination[i] > 2:
                    combination[i] = combination[i] - 1
                    combination[count] = combination[count] + 1
                    break
                i -= 1
        count -= 1

    return combination


# validate ip address
def ip_is_valid(ip):
    adr = ip.split(".")
    for n in adr:
        num = int(n)
        if num < 0:
            return False
        if num > 255:
            return False
        if len(list(n)) == 2 and num < 10:
            return False
        if len(list(n)) == 3 and num < 100:
            return False
    return True


def formulate_ip(numbers, pattern):
    num_list = list(numbers)
    ip = []
    count = 0
    i = 0
    while count < (len(num_list) + 3):
        if (
            count == pattern[0]
            or count == pattern[0] + pattern[1] + 1
            or count == pattern[0] + pattern[1] + pattern[2] + 2
        ):
            ip.append(".")
            count += 1
            continue
        ip.append(num_list[i])
        i += 1
        count += 1
    formulated_ip = "".join(ip)
    # print(formulated_ip)
    return formulated_ip


def print_valid_ip(number_string, permutations):
    for permutation in permutations:
        ip = formulate_ip(number_string, permutation)
        if ip_is_valid(ip):
            print(ip)


# get input from console and convert to a list of integers
number_string = raw_input("enter number string:")
map_object = map(int, list(number_string))
numbers = list(map_object)
# permutations without zeros
com = get_valid_combinations_without_0(get_possible_count_combinations(len(numbers)))
list_1 = generate_permutations(com)
# permutatios with no ones, if possible
com2 = get_valid_combinations_without_1(com)
list_2 = generate_permutations(com2)
# join the two lists and remove duplicates
_lst = list_1 + list_2

lst = [i for n, i in enumerate(_lst) if i not in _lst[:n]]
print_valid_ip(number_string, lst)
