from simulators.cdr import CDRSimulator
from simulators.device import DeviceSimulator
from simulators.sse import SSESimulator

registry = {
    "cdr": CDRSimulator,
    "device": DeviceSimulator,
    "sse": SSESimulator
    # Add more as needed
}
