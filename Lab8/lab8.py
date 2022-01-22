import numpy as np
import matplotlib.pyplot as plt


def free_space(freq, dist):
    return -27.55 + 20 * np.log10(freq) + 20 * np.log10(dist)


def itur(freq, dist, N, ceilDamping, ceils):
    return 20 * np.log10(freq) + N * np.log10(dist) + ceilDamping*ceils - 28


def one_slope(freq, dist, y):
    return free_space(freq, 1) + 10 * y * np.log10(dist)


def motley_keenan(freq, dist, walls, ceils, wallDamping, ceilDamping):
    return free_space(freq, dist) + walls * wallDamping + ceils * ceilDamping


def multi_wall(freq, dist, y, obstacles):
    sumDamp = 0
    for obstacle in obstacles:
        sumDamp += obstacle[0] * obstacle[1]

    return free_space(freq, 1) + 10*y * np.log10(dist) + sumDamp


frequency = 2412
n_walls = 1
n_ceils = 2
outer_wall = 9
inner_wall = 7
ceil_damp = 11
window = 4.5
distance = 7
iturN = 28
_y = 4

print(20-free_space(frequency, distance))
print(20-itur(frequency, distance, iturN, ceil_damp, n_ceils))
print(20-one_slope(frequency, distance, _y))
print(20-motley_keenan(frequency, distance, n_walls, n_ceils, inner_wall, ceil_damp))
print(20-multi_wall(frequency, distance, _y, [[window, 1]]))
