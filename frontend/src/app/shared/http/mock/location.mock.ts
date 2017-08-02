/*
 * Mock class for Location interface returned from 'window.location'.
 */
export class MockLocation implements Location {
    public hash: string;
    public host: string;
    public hostname: string;
    public href: string;
    public origin: string;
    public pathname: string;
    public port: string;
    public protocol: string;
    public search: string;

    public assign(url: string): void {
    }

    public reload(forcedReload?: boolean): void {
    }

    public replace(url: string): void {
    }

}
