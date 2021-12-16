import math
import matplotlib.pyplot as plt
from matplotlib.ticker import MaxNLocator
import numpy as np

lightspeed = 299_792_458 # m/s
PI = math.pi


def power_drop_free(distance_m, frequency_Hz, Gt, Gr):
    _len = lightspeed/frequency_Hz
    pd = Gt*Gr*((_len / (4 * PI * distance_m)) ** 2)
    return 10*np.log10(pd)


def power_drop_multi(distance1_m, distance2_m, frequency_Hz, Gt, Gr):
    _len = lightspeed / frequency_Hz
    angle1 = -2 * PI * frequency_Hz * (distance1_m / lightspeed)
    angle2 = -2 * PI * frequency_Hz * (distance2_m / lightspeed)

    pd = Gt * Gr * ((_len / (4 * PI)) ** 2) * np.abs((1 / distance1_m) * np.exp(1j * angle1) - (1 / distance2_m) * np.exp(1j * angle2)) ** 2
    return 10*np.log10(pd)


def plot_both(_ax, _h1, _h2, distance_m, freq_Hz, Gt, Gr):
    x = np.arange(1, distance_m + 1, step).astype(np.float32)
    d1 = np.sqrt(np.power(x, 2) + (_h1 - _h2) ** 2)
    d2 = np.sqrt(np.power(x, 2) + (_h1 + _h2) ** 2)
    _ax.plot(x, power_drop_free(x, freq_Hz, Gt, Gr), label='Free propagation')
    _ax.plot(x, power_drop_multi(d1, d2, freq_Hz, Gt, Gr), label='Multipath propagation')
    _ax.set(xlabel='Distance (m)', ylabel='Relative power drop (dB)')
    _ax.set_title(f'Freq = {int(freq_Hz / 10 ** 6)}MHz')


def plot_free(_ax, distance_m, freq_Hz, Gt, Gr):
    x = np.arange(1, distance_m + 1, step).astype(np.float32)
    _ax.plot(x, power_drop_free(x, freq_Hz, Gt, Gr), label='Free propagation')
    _ax.set(xlabel='Distance (m)', ylabel='Relative power drop (dB)')
    _ax.set_title(f'Freq = {int(freq_Hz / 10 ** 6)}MHz')


def plot_multi(_ax, _h1, _h2, distance_m, freq_Hz, Gt, Gr):
    x = np.arange(1, distance_m + 1, step).astype(np.float32)
    d1 = np.sqrt(np.power(x, 2) + (_h1 - _h2) ** 2)
    d2 = np.sqrt(np.power(x, 2) + (_h1 + _h2) ** 2)
    _ax.plot(x, power_drop_multi(d1, d2, freq_Hz, Gt, Gr), label='Multipath propagation')
    _ax.set(xlabel='Distance (m)', ylabel='Relative power drop (dB)')
    _ax.set_title(f'Freq = {int(freq_Hz / 10 ** 6)}MHz')


step = 0.25
distance1 = 100
distance2 = 10_000
freq1 = 900_000_000
freq2 = 2400_000_000
h1 = 30
h2 = 3

x1 = np.arange(1, distance1+1, step).astype(np.float32)
x2 = np.arange(1, distance2+1, step).astype(np.float32)

x1d1 = np.sqrt(np.power(x1, 2)+(h1-h2)**2)
x1d2 = np.sqrt(np.power(x1, 2)+(h1+h2)**2)

x2d1 = np.sqrt(np.power(x2, 2)+(h1-h2)**2)
x2d2 = np.sqrt(np.power(x2, 2)+(h1+h2)**2)

print(power_drop_free(1, lightspeed/0.3, 1.6, 1.6))


# delay
fig, ax = plt.subplots(1, 1)
ax.plot(x2, x2/lightspeed)
fig.tight_layout(pad=3.0)
ax.set(xlabel='Distance (m)', ylabel='Delay (s)')
plt.show()

# propagation free
fig, ax = plt.subplots(2, 2, figsize=(16, 10))
fig.tight_layout(pad=10.0)
plot_free(ax[0][0], distance1, freq1, 1.6, 1.6)
plot_free(ax[0][1], distance1, freq2, 1.6, 1.6)
plot_free(ax[1][0], distance2, freq1, 1.6, 1.6)
plot_free(ax[1][1], distance2, freq2, 1.6, 1.6)
plt.show()

# propagation multipath
fig, ax = plt.subplots(2, 2, figsize=(16, 10))
fig.tight_layout(pad=10.0)
plot_multi(ax[0][0], h1, h2, distance1, freq1, 1.6, 1.6)
plot_multi(ax[0][1], h1, h2, distance1, freq2, 1.6, 1.6)
plot_multi(ax[1][0], h1, h2, distance2, freq1, 1.6, 1.6)
plot_multi(ax[1][1], h1, h2, distance2, freq2, 1.6, 1.6)
plt.show()

# propagation both
fig, ax = plt.subplots(2, 2, figsize=(16, 10))
fig.tight_layout(pad=10.0)
plot_both(ax[0][0], h1, h2, distance1, freq1, 1.6, 1.6)
plot_both(ax[0][1], h1, h2, distance1, freq2, 1.6, 1.6)
plot_both(ax[1][0], h1, h2, distance2, freq1, 1.6, 1.6)
plot_both(ax[1][1], h1, h2, distance2, freq2, 1.6, 1.6)
plt.show()



