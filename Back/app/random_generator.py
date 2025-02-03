import random

class RandomNumberGenerator:
    def __init__(self):
        self.used_numbers = set()

    def generate_unique_random_number(self, lower_bound = 100000, upper_bound = 999999):
        while True:
            random_number = random.randint(lower_bound, upper_bound)
            if random_number not in self.used_numbers:
                self.used_numbers.add(random_number)
                return random_number