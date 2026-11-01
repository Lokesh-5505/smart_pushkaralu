import 'dart:async';

import 'package:flutter/material.dart';

void main() => runApp(const SmartPushkaraluApp());

enum AppLanguage { telugu, english, hindi }

enum AppTab { home, planner, routes, stays, parking, group, safety, settings }

enum Density { low, moderate, high, critical }

class Ghat {
  const Ghat(this.name, this.district, this.current, this.capacity, this.density);
  final String name;
  final String district;
  final int current;
  final int capacity;
  final Density density;
}

class Alert {
  const Alert(this.title, this.message, this.emergency);
  final String title;
  final String message;
  final bool emergency;
}

class SmartPushkaraluApp extends StatefulWidget {
  const SmartPushkaraluApp({super.key});

  @override
  State<SmartPushkaraluApp> createState() => _SmartPushkaraluAppState();
}

class _SmartPushkaraluAppState extends State<SmartPushkaraluApp> {
  AppLanguage language = AppLanguage.telugu;
  AppTab tab = AppTab.home;
  bool highContrast = false;
  bool largeText = false;
  bool surge = false;
  bool parkingFull = false;
  bool ambulance = false;
  bool sosActive = false;
  int sosCountdown = 3;
  Timer? sosTimer;
  String category = 'General Pilgrim';

  final ghats = <Ghat>[
    const Ghat('Pushkar Ghat', 'East Godavari', 7820, 8000, Density.high),
    const Ghat('Kovvur Goshpada Kshetram', 'East Godavari', 1840, 6500, Density.low),
    const Ghat('Dowleswaram Barrage', 'East Godavari', 3820, 7000, Density.moderate),
    const Ghat('Antarvedi Ghat', 'Konaseema', 920, 4200, Density.low),
  ];

  @override
  void dispose() {
    sosTimer?.cancel();
    super.dispose();
  }

  List<Alert> get alerts => [
        if (surge)
          const Alert(
            'Crowd surge: Pushkar Ghat entry locked',
            'Please follow diversion signs to Kovvur Goshpada Kshetram.',
            true,
          ),
        if (parkingFull)
          const Alert(
            'Parking Zone A is full',
            'Vehicles are being intercepted at Arts College Remote Hub.',
            true,
          ),
        if (ambulance)
          const Alert(
            'Smart ambulance corridor active',
            'Godavari Bund Road has priority clearance for emergency vehicles.',
            true,
          ),
        const Alert(
          'Godavari water flow is safe',
          'River telemetry and medical posts are operating normally.',
          false,
        ),
      ];

  void triggerSos() {
    sosTimer?.cancel();
    setState(() {
      sosActive = true;
      sosCountdown = 3;
    });
    sosTimer = Timer.periodic(const Duration(seconds: 1), (timer) {
      if (!mounted) return;
      setState(() => sosCountdown--);
      if (sosCountdown == 0) timer.cancel();
    });
  }

  void cancelSos() {
    sosTimer?.cancel();
    setState(() {
      sosActive = false;
      sosCountdown = 3;
    });
  }

  void simulate(String type) {
    setState(() {
      if (type == 'surge') surge = !surge;
      if (type == 'parking') parkingFull = !parkingFull;
      if (type == 'ambulance') ambulance = !ambulance;
    });
  }

  @override
  Widget build(BuildContext context) {
    final seed = highContrast ? Colors.black : const Color(0xff12345b);
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Smart Pushkaralu',
      theme: ThemeData(
        useMaterial3: true,
        colorScheme: ColorScheme.fromSeed(seedColor: seed),
        scaffoldBackgroundColor: const Color(0xfff5f8fc),
        textTheme: ThemeData.light().textTheme.apply(
              fontSizeFactor: largeText ? 1.18 : 1,
            ),
      ),
      home: Builder(
        builder: (context) => Scaffold(
          appBar: _appBar(context),
          body: SafeArea(
            child: LayoutBuilder(
              builder: (context, constraints) {
                final content = _screen();
                if (constraints.maxWidth >= 900) {
                  return Row(
                    children: [
                      NavigationRail(
                        selectedIndex: tab.index,
                        onDestinationSelected: (index) =>
                            setState(() => tab = AppTab.values[index]),
                        labelType: NavigationRailLabelType.all,
                        destinations: _railDestinations(),
                      ),
                      Expanded(child: Center(child: ConstrainedBox(
                        constraints: const BoxConstraints(maxWidth: 1100),
                        child: content,
                      ))),
                    ],
                  );
                }
                return content;
              },
            ),
          ),
          bottomNavigationBar: MediaQuery.sizeOf(context).width < 900
              ? NavigationBar(
                  selectedIndex: _mobileTabIndex(),
                  onDestinationSelected: (index) => setState(() {
                    tab = const [
                      AppTab.home,
                      AppTab.routes,
                      AppTab.stays,
                      AppTab.parking,
                      AppTab.settings,
                    ][index];
                  }),
                  destinations: const [
                    NavigationDestination(icon: Icon(Icons.home_outlined), selectedIcon: Icon(Icons.home), label: 'Home'),
                    NavigationDestination(icon: Icon(Icons.route_outlined), selectedIcon: Icon(Icons.route), label: 'Routes'),
                    NavigationDestination(icon: Icon(Icons.hotel_outlined), selectedIcon: Icon(Icons.hotel), label: 'Stays'),
                    NavigationDestination(icon: Icon(Icons.local_parking_outlined), selectedIcon: Icon(Icons.local_parking), label: 'Parking'),
                    NavigationDestination(icon: Icon(Icons.settings_outlined), selectedIcon: Icon(Icons.settings), label: 'More'),
                  ],
                )
              : null,
        ),
      ),
    );
  }

  PreferredSizeWidget _appBar(BuildContext context) {
    return AppBar(
      backgroundColor: const Color(0xff12345b),
      foregroundColor: Colors.white,
      title: const Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text('SMART PUSHKARALU', style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold)),
          Text('Godavari 2027 • Public Safety', style: TextStyle(fontSize: 11)),
        ],
      ),
      actions: [
        PopupMenuButton<AppLanguage>(
          tooltip: 'Language',
          icon: const Icon(Icons.translate),
          onSelected: (value) => setState(() => language = value),
          itemBuilder: (context) => const [
            PopupMenuItem(value: AppLanguage.telugu, child: Text('తెలుగు')),
            PopupMenuItem(value: AppLanguage.english, child: Text('English')),
            PopupMenuItem(value: AppLanguage.hindi, child: Text('हिंदी')),
          ],
        ),
        IconButton(
          tooltip: 'Emergency SOS',
          onPressed: triggerSos,
          icon: const Icon(Icons.sos, color: Color(0xffffb4ab)),
        ),
      ],
    );
  }

  List<NavigationRailDestination> _railDestinations() => const [
        NavigationRailDestination(icon: Icon(Icons.home_outlined), selectedIcon: Icon(Icons.home), label: Text('Home')),
        NavigationRailDestination(icon: Icon(Icons.event_note_outlined), selectedIcon: Icon(Icons.event_note), label: Text('Planner')),
        NavigationRailDestination(icon: Icon(Icons.route_outlined), selectedIcon: Icon(Icons.route), label: Text('Routes')),
        NavigationRailDestination(icon: Icon(Icons.hotel_outlined), selectedIcon: Icon(Icons.hotel), label: Text('Stays')),
        NavigationRailDestination(icon: Icon(Icons.local_parking_outlined), selectedIcon: Icon(Icons.local_parking), label: Text('Parking')),
        NavigationRailDestination(icon: Icon(Icons.groups_outlined), selectedIcon: Icon(Icons.groups), label: Text('Family')),
        NavigationRailDestination(icon: Icon(Icons.medical_services_outlined), selectedIcon: Icon(Icons.medical_services), label: Text('Safety')),
        NavigationRailDestination(icon: Icon(Icons.settings_outlined), selectedIcon: Icon(Icons.settings), label: Text('Settings')),
      ];

  Widget _screen() {
    switch (tab) {
      case AppTab.home:
        return _home();
      case AppTab.planner:
        return _planner();
      case AppTab.routes:
        return _routes();
      case AppTab.stays:
        return _stays();
      case AppTab.parking:
        return _parking();
      case AppTab.group:
        return _family();
      case AppTab.safety:
        return _safety();
      case AppTab.settings:
        return _settings();
    }

    int _mobileTabIndex() => switch (tab) {
          AppTab.home => 0,
          AppTab.routes => 1,
          AppTab.stays => 2,
          AppTab.parking => 3,
          _ => 4,
        };
  }

  Widget _home() {
    return _scroll([
      _categoryCard(),
      ...alerts.map((alert) => _alertCard(alert)),
      _sectionTitle('Live ghat crowd density', '440+ ghats monitored by the command centre'),
      SizedBox(
        height: 170,
        child: ListView.separated(
          scrollDirection: Axis.horizontal,
          padding: const EdgeInsets.symmetric(horizontal: 16),
          itemCount: ghats.length,
          separatorBuilder: (_, __) => const SizedBox(width: 12),
          itemBuilder: (_, index) => _ghatCard(ghats[index]),
        ),
      ),
      _sectionTitle('Safety services', 'Fast access to support and assistance'),
      _serviceGrid(context),
      _simulationBar(),
    ]);
  }

  Widget _categoryCard() => Card(
        margin: const EdgeInsets.all(16),
        color: const Color(0xff12345b),
        child: ListTile(
          leading: const CircleAvatar(backgroundColor: Color(0xffffd66b), child: Icon(Icons.groups, color: Color(0xff12345b))),
          title: const Text('CATEGORY ZONING • UNIDIRECTIONAL FLOW', style: TextStyle(color: Color(0xffffd66b), fontSize: 11)),
          subtitle: Text('$category\nAssigned Entry: Gate C (Main Flow)', style: const TextStyle(color: Colors.white, fontWeight: FontWeight.bold)),
          trailing: IconButton(onPressed: () => setState(() => tab = AppTab.settings), icon: const Icon(Icons.edit, color: Colors.white)),
        ),
      );

  Widget _alertCard(Alert alert) => Card(
        margin: const EdgeInsets.fromLTRB(16, 0, 16, 8),
        color: alert.emergency ? const Color(0xffffe5e2) : const Color(0xffe6f4ff),
        child: ListTile(
          leading: Icon(alert.emergency ? Icons.warning_amber : Icons.info_outline, color: alert.emergency ? Colors.red : Colors.blue),
          title: Text(alert.title, style: const TextStyle(fontWeight: FontWeight.bold)),
          subtitle: Text(alert.message),
        ),
      );

  Widget _ghatCard(Ghat ghat) {
    final percent = ghat.current / ghat.capacity;
    return SizedBox(
      width: 235,
      child: Card(
        child: Padding(
          padding: const EdgeInsets.all(14),
          child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
            Text(ghat.name, maxLines: 2, overflow: TextOverflow.ellipsis, style: const TextStyle(fontWeight: FontWeight.bold)),
            Text(ghat.district, style: const TextStyle(color: Colors.grey)),
            const Spacer(),
            Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
              _densityBadge(ghat.density),
              Text('${(percent * 100).round()}%', style: const TextStyle(fontWeight: FontWeight.bold)),
            ]),
            const SizedBox(height: 8),
            LinearProgressIndicator(value: percent, color: _densityColor(ghat.density)),
          ]),
        ),
      ),
    );
  }

  Widget _serviceGrid(BuildContext context) => GridView.count(
        crossAxisCount: MediaQuery.sizeOf(context).width > 650 ? 4 : 2,
        shrinkWrap: true,
        physics: const NeverScrollableScrollPhysics(),
        padding: const EdgeInsets.symmetric(horizontal: 16),
        crossAxisSpacing: 10,
        mainAxisSpacing: 10,
        childAspectRatio: 1.35,
        children: [
          _service(Icons.event_note, 'Trip Planner', AppTab.planner),
          _service(Icons.route, 'Safe Navigation', AppTab.routes),
          _service(Icons.medical_services, 'Safety & Medical', AppTab.safety),
          _service(Icons.groups, 'Family Group', AppTab.group),
        ],
      );

  Widget _service(IconData icon, String title, AppTab destination) => Card(
        child: InkWell(
          onTap: () => setState(() => tab = destination),
          child: Column(mainAxisAlignment: MainAxisAlignment.center, children: [
            Icon(icon, size: 30, color: const Color(0xff0a7890)),
            const SizedBox(height: 7),
            Text(title, textAlign: TextAlign.center, style: const TextStyle(fontWeight: FontWeight.bold)),
          ]),
        ),
      );

  Widget _planner() {
    return _scroll([
      _pageHeader('Pre-trip crowd dispersal', 'Assign a safe, low-density bathing slot based on your group.'),
      _choiceCard('Budget preference', ['Economy (₹0–₹800)', 'Standard (₹800–₹2,000)', 'Comfort (₹2,000–₹4,500)', 'Premium (₹4,500+)']),
      _counterCard('Total group size', 'Members traveling together', 4),
      _counterCard('Elderly devotees (65+)', 'Enforces cool morning slots and step-free ramps', 1),
      _counterCard('Young children (<12)', 'Assigns RFID wristband desks', 1),
      Card(child: SwitchListTile(value: true, onChanged: (_) {}, title: const Text('Include temple circuit'), subtitle: const Text('Pithapuram • Annavaram • Dwaraka Tirumala • Antarvedi'))),
      FilledButton.icon(onPressed: () => _showMessage('Plan generated: 7:00 AM Kovvur slot • ₹18,400 estimated'), icon: const Icon(Icons.auto_awesome), label: const Text('Generate safe itinerary')),
      _resultCard(),
    ]);
  }

  Widget _choiceCard(String title, List<String> options) => Card(
        child: Padding(
          padding: const EdgeInsets.all(14),
          child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
            Text(title, style: const TextStyle(fontWeight: FontWeight.bold)),
            ...options.map((option) => RadioListTile<String>(dense: true, value: option, groupValue: options[1], onChanged: (_) {}, title: Text(option))),
          ]),
        ),
      );

  Widget _counterCard(String title, String subtitle, int value) => Card(
        child: ListTile(
          title: Text(title, style: const TextStyle(fontWeight: FontWeight.bold)),
          subtitle: Text(subtitle),
          trailing: Row(mainAxisSize: MainAxisSize.min, children: [
            IconButton(onPressed: () {}, icon: const Icon(Icons.remove_circle_outline)),
            Text('$value', style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
            IconButton(onPressed: () {}, icon: const Icon(Icons.add_circle_outline)),
          ]),
        ),
      );

  Widget _resultCard() => Card(
        color: const Color(0xffe7f7ee),
        child: const ListTile(
          leading: Icon(Icons.verified, color: Colors.green),
          title: Text('Recommended safe itinerary', style: TextStyle(fontWeight: FontWeight.bold)),
          subtitle: Text('Kovvur Goshpada Kshetram • 7:00–8:30 AM\nTrain to Rajahmundry + free feeder bus'),
        ),
      );

  Widget _routes() => _scroll([
        _pageHeader('Safe navigation', 'Routes avoid closed, throttled, and high-density ghats.'),
        if (surge) _alertCard(const Alert('Diversion active', 'Pushkar Ghat is locked. Use the Rail-cum-Road bridge to Kovvur.', true)),
        _routeCard('Fastest', '32 min • 11.4 km', 'Godavari Bund Road', false),
        _routeCard('Least crowded', '41 min • 14.2 km', 'Rail-cum-Road bridge → Kovvur', true),
        _routeCard('Easiest / step-free', '48 min • 16.8 km', 'Accessible feeder shuttle route', true),
        _pageHeader('Route safety rules', 'Live density and emergency corridors are refreshed automatically.'),
      ]);

  Widget _routeCard(String title, String metrics, String summary, bool safe) => Card(
        child: ListTile(
          leading: Icon(safe ? Icons.check_circle : Icons.route, color: safe ? Colors.green : Colors.orange),
          title: Text(title, style: const TextStyle(fontWeight: FontWeight.bold)),
          subtitle: Text('$metrics\n$summary'),
          trailing: safe ? const Chip(label: Text('SAFE')) : const Chip(label: Text('WATCH')),
        ),
      );

  Widget _stays() => _scroll([
        _pageHeader('Verified stay directory', 'Fixed festival rate bands prevent price gouging.'),
        Wrap(spacing: 8, children: [
          FilterChip(label: const Text('Pure veg'), selected: false, onSelected: (_) {}),
          FilterChip(label: const Text('Step-free'), selected: false, onSelected: (_) {}),
          FilterChip(label: const Text('Parking'), selected: false, onSelected: (_) {}),
        ]),
        ...[
          ('River Bay Resort', 'East Godavari', '₹3,800/night'),
          ('Anand Regency', 'Rajahmundry', '₹1,850/night'),
          ('Kovvur Haritha', 'Kovvur', '₹1,200/night'),
          ('Jetty Grand', 'Rajahmundry', '₹2,900/night'),
        ].map((stay) => Card(child: ListTile(leading: const Icon(Icons.hotel, color: Color(0xff0a7890)), title: Text(stay.$1, style: const TextStyle(fontWeight: FontWeight.bold)), subtitle: Text('${stay.$2} • Government verified\n${stay.$3}'), trailing: const Icon(Icons.chevron_right)))),
      ]);

  Widget _parking() => _scroll([
        _pageHeader('Smart parking & ANPR', 'Upstream interception keeps narrow river roads clear.'),
        Card(child: ListTile(leading: const Icon(Icons.directions_car), title: const Text('Registered vehicle'), subtitle: const Text('AP39 AB 1234\nPass active for Pushkaralu 2027'))),
        _parkingCard('Pushkar Circle Zone A', parkingFull ? 100 : 68, parkingFull ? 'FULL — OVERFLOW ACTIVE' : 'FILLING FAST'),
        _parkingCard('Arts College Remote Hub', 24, 'FREE SLOTS'),
        if (parkingFull) _alertCard(const Alert('Intercept instruction', 'Proceed to Arts College Remote Hub. Free electric feeder shuttle every 8 minutes.', true)),
      ]);

  Widget _parkingCard(String title, int percent, String status) => Card(
        child: Padding(
          padding: const EdgeInsets.all(16),
          child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
            Text(title, style: const TextStyle(fontWeight: FontWeight.bold)),
            const SizedBox(height: 8),
            LinearProgressIndicator(value: percent / 100, color: percent > 90 ? Colors.red : Colors.teal),
            const SizedBox(height: 8),
            Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [Text(status), Text('$percent%')]),
          ]),
        ),
      );

  Widget _family() => _scroll([
        _pageHeader('My family group', 'Keep dependents together with proximity and RFID alerts.'),
        Card(child: ListTile(leading: const CircleAvatar(child: Icon(Icons.person)), title: const Text('Lokesh (Group Head)'), subtitle: const Text('4 members • Live location sharing enabled'))),
        ...[
          ('Ananya', 'Child • RFID #AP-PK-8821', Icons.child_care),
          ('Ravi', 'Father • Step-free assistance', Icons.accessible),
          ('Sita', 'Mother • Safe', Icons.person),
        ].map((member) => Card(child: ListTile(leading: Icon(member.$3, color: const Color(0xff0a7890)), title: Text(member.$1), subtitle: Text(member.$2), trailing: const Text('3 m')))),
        FilledButton.icon(onPressed: () => _showMessage('Family location link copied'), icon: const Icon(Icons.share), label: const Text('Share family location')),
      ]);

  Widget _safety() => _scroll([
        _pageHeader('Safety & medical', 'Rapid emergency de-escalation and medical support.'),
        Card(color: const Color(0xffffe5e2), child: Padding(padding: const EdgeInsets.all(18), child: Column(children: [
          const Icon(Icons.sos, size: 58, color: Colors.red),
          const SizedBox(height: 8),
          const Text('1-TAP EMERGENCY SOS', style: TextStyle(fontWeight: FontWeight.bold, fontSize: 18)),
          const Text('Sends your location to Sector 4 officers, medical posts, and river rescue boats.', textAlign: TextAlign.center),
          const SizedBox(height: 12),
          FilledButton.tonal(onPressed: sosActive ? null : triggerSos, child: const Text('ACTIVATE SOS')),
        ]))),
        if (sosActive) Card(color: const Color(0xffffd7d2), child: ListTile(title: Text(sosCountdown == 0 ? 'Officer dispatched' : 'Dispatching in $sosCountdown…'), subtitle: Text(sosCountdown == 0 ? 'Inspector K. R. Rao • ETA 2.4 minutes' : 'Tap cancel if this was accidental.'), trailing: TextButton(onPressed: cancelSos, child: const Text('CANCEL')))),
        Card(child: ListTile(leading: const Icon(Icons.video_call), title: const Text('E-Clinic tele-consultation'), subtitle: const Text('Connect to the nearest medical station'), trailing: FilledButton(onPressed: () => _showMessage('E-Clinic request sent'), child: const Text('OPEN')))),
        Card(child: ListTile(leading: const Icon(Icons.person_search), title: const Text('Missing person report'), subtitle: const Text('Notify field officers and nearby gates'), trailing: FilledButton(onPressed: () => _showMessage('Missing person desk notified'), child: const Text('REPORT')))),
        Card(child: ListTile(leading: const Icon(Icons.water), title: const Text('River telemetry'), subtitle: const Text('Water level 14.2 m • Flow safe • 18 medical posts active'))),
      ]);

  Widget _settings() => _scroll([
        _pageHeader('Profile & settings', 'Personalize accessibility and pilgrim category guidance.'),
        Card(child: Column(children: [
          ListTile(title: const Text('Pilgrim category'), subtitle: Text(category), trailing: DropdownButton<String>(value: category, underline: const SizedBox(), items: const ['General Pilgrim', 'Ritual Performer', 'Family with Young Children', 'Differently-Abled & Senior Citizens'].map((value) => DropdownMenuItem(value: value, child: Text(value, overflow: TextOverflow.ellipsis))).toList(), onChanged: (value) => setState(() => category = value!))),
          SwitchListTile(value: largeText, onChanged: (value) => setState(() => largeText = value), title: const Text('Large typography'), subtitle: const Text('Improves readability for seniors')),
          SwitchListTile(value: highContrast, onChanged: (value) => setState(() => highContrast = value), title: const Text('High contrast'), subtitle: const Text('Stronger text and control contrast')),
        ])),
        _pageHeader('Language', 'Text and voice prompts use the selected language.'),
        SegmentedButton<AppLanguage>(segments: const [
          ButtonSegment(value: AppLanguage.telugu, label: Text('తెలుగు')),
          ButtonSegment(value: AppLanguage.english, label: Text('English')),
          ButtonSegment(value: AppLanguage.hindi, label: Text('हिंदी')),
        ], selected: {language}, onSelectionChanged: (values) => setState(() => language = values.first)),
        _simulationBar(),
      ]);

  Widget _simulationBar() => Card(
        margin: const EdgeInsets.all(16),
        color: const Color(0xff102a43),
        child: Padding(
          padding: const EdgeInsets.all(14),
          child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
            const Text('GOVERNMENT SHOWCASE SIMULATION', style: TextStyle(color: Color(0xffffd66b), fontWeight: FontWeight.bold, fontSize: 11)),
            const SizedBox(height: 8),
            Wrap(spacing: 8, runSpacing: 8, children: [
              OutlinedButton(onPressed: () => simulate('surge'), child: Text(surge ? 'Reset crowd surge' : 'Simulate crowd surge')),
              OutlinedButton(onPressed: () => simulate('parking'), child: Text(parkingFull ? 'Reset parking' : 'Simulate parking full')),
              OutlinedButton(onPressed: () => simulate('ambulance'), child: Text(ambulance ? 'Reset corridor' : 'Simulate ambulance corridor')),
            ]),
          ]),
        ),
      );

  Widget _sectionTitle(String title, String subtitle) => Padding(
        padding: const EdgeInsets.fromLTRB(16, 12, 16, 8),
        child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
          Text(title, style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
          Text(subtitle, style: const TextStyle(color: Colors.grey)),
        ]),
      );

  Widget _pageHeader(String title, String subtitle) => Padding(
        padding: const EdgeInsets.fromLTRB(16, 18, 16, 8),
        child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
          Text(title, style: const TextStyle(fontSize: 24, fontWeight: FontWeight.bold)),
          Text(subtitle, style: const TextStyle(color: Colors.grey)),
        ]),
      );

  Widget _scroll(List<Widget> children) => ListView(
        key: ValueKey(tab),
        padding: const EdgeInsets.only(bottom: 24),
        children: children,
      );

  Widget _densityBadge(Density density) => Container(
        padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
        decoration: BoxDecoration(color: _densityColor(density).withOpacity(.14), borderRadius: BorderRadius.circular(20)),
        child: Text(_densityLabel(density), style: TextStyle(color: _densityColor(density), fontWeight: FontWeight.bold, fontSize: 11)),
      );

  Color _densityColor(Density density) => switch (density) {
        Density.low => Colors.green,
        Density.moderate => Colors.orange,
        Density.high => Colors.deepOrange,
        Density.critical => Colors.red,
      };

  String _densityLabel(Density density) => switch (density) {
        Density.low => 'LOW',
        Density.moderate => 'MODERATE',
        Density.high => 'HIGH',
        Density.critical => 'CRITICAL',
      };

  void _showMessage(String message) {
    ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text(message)));
  }
}
