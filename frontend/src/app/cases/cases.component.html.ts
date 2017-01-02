export const HTML_TEMPLATE = `
    <control-panel-overview (loadCases)="loadCases($event)"></control-panel-overview>
    <educama-tabset>
        <educama-tab tabTitle="Instanzen / Transportauftrag">
            <cases-instances></cases-instances>
        </educama-tab>
<!--    <educama-tab tabTitle="Aufgaben">
            <cases-tasks></cases-tasks>
        </educama-tab>  -->
    </educama-tabset>
`;