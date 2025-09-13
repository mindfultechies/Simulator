import time

def rate_controller(events_per_second):
    interval = 1.0 / events_per_second
    while True:
        yield True
        time.sleep(interval)
