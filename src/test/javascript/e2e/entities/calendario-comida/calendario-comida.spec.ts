import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { CalendarioComidaComponentsPage, CalendarioComidaUpdatePage } from './calendario-comida.page-object';

describe('CalendarioComida e2e test', () => {
    let navBarPage: NavBarPage;
    let calendarioComidaUpdatePage: CalendarioComidaUpdatePage;
    let calendarioComidaComponentsPage: CalendarioComidaComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load CalendarioComidas', () => {
        navBarPage.goToEntity('calendario-comida');
        calendarioComidaComponentsPage = new CalendarioComidaComponentsPage();
        expect(calendarioComidaComponentsPage.getTitle()).toMatch(/adminhouseApp.calendarioComida.home.title/);
    });

    it('should load create CalendarioComida page', () => {
        calendarioComidaComponentsPage.clickOnCreateButton();
        calendarioComidaUpdatePage = new CalendarioComidaUpdatePage();
        expect(calendarioComidaUpdatePage.getPageTitle()).toMatch(/adminhouseApp.calendarioComida.home.createOrEditLabel/);
        calendarioComidaUpdatePage.cancel();
    });

    it('should create and save CalendarioComidas', () => {
        calendarioComidaComponentsPage.clickOnCreateButton();
        calendarioComidaUpdatePage.setFechaInput('2000-12-31');
        expect(calendarioComidaUpdatePage.getFechaInput()).toMatch('2000-12-31');
        calendarioComidaUpdatePage.grupoSelectLastOption();
        calendarioComidaUpdatePage.diaSelectLastOption();
        calendarioComidaUpdatePage.save();
        expect(calendarioComidaUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
