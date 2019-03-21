import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { DiaComponentsPage, DiaUpdatePage } from './dia.page-object';

describe('Dia e2e test', () => {
    let navBarPage: NavBarPage;
    let diaUpdatePage: DiaUpdatePage;
    let diaComponentsPage: DiaComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Dias', () => {
        navBarPage.goToEntity('dia');
        diaComponentsPage = new DiaComponentsPage();
        expect(diaComponentsPage.getTitle()).toMatch(/adminhouseApp.dia.home.title/);
    });

    it('should load create Dia page', () => {
        diaComponentsPage.clickOnCreateButton();
        diaUpdatePage = new DiaUpdatePage();
        expect(diaUpdatePage.getPageTitle()).toMatch(/adminhouseApp.dia.home.createOrEditLabel/);
        diaUpdatePage.cancel();
    });

    it('should create and save Dias', () => {
        diaComponentsPage.clickOnCreateButton();
        diaUpdatePage.diaSemanaSelectLastOption();
        diaUpdatePage.grupoSelectLastOption();
        diaUpdatePage.save();
        expect(diaUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
