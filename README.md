# bitcoin_rates_mvp_rx


What's done:

1) Async bitcoin rates retrieval

2) MVP structure

3) RX integration

4) Options for different time periods

5) One graph renderer class and one prototype

6) One GraphView that can by stylized

7) Rendering supports touch events

What I didn't do, but would like to see in a production ready class:

1) State saving of the renderer

2) Offline cache of rates and results

3) Limits to zoom and translations

4) Separate geometry for Labels and graphs

5) Better error handling

6) Base activity with all needed methods

7) Animations for bars loading and appearing

8) Integration tests for repo methods

IMO all that is easily done with the current architecture and more time. Personal learnings for me - RX is helpful for quick setup of an ASYNC pipeline. Got better understanding of Canvas class and Android API rendering. Honestly - OpenGL seems more robust and just as painful to setup.
