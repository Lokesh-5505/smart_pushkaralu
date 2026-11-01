import 'package:flutter_test/flutter_test.dart';
import 'package:smart_pushkaralu/main.dart';

void main() {
  testWidgets('renders home dashboard and navigation', (tester) async {
    await tester.pumpWidget(const SmartPushkaraluApp());

    expect(find.text('SMART PUSHKARALU'), findsOneWidget);
    expect(find.text('Live ghat crowd density'), findsOneWidget);
    expect(find.text('Trip Planner'), findsOneWidget);

    await tester.tap(find.text('Trip Planner'));
    await tester.pumpAndSettle();
    expect(find.text('Pre-trip crowd dispersal'), findsOneWidget);
  });

  testWidgets('simulation updates emergency alert', (tester) async {
    await tester.pumpWidget(const SmartPushkaraluApp());

    await tester.tap(find.text('Simulate crowd surge'));
    await tester.pump();

    expect(find.text('Crowd surge: Pushkar Ghat entry locked'), findsOneWidget);
  });
}
