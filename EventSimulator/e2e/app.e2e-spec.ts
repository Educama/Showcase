import { EventSimulatorPage } from './app.po';

describe('event-simulator App', () => {
  let page: EventSimulatorPage;

  beforeEach(() => {
    page = new EventSimulatorPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
