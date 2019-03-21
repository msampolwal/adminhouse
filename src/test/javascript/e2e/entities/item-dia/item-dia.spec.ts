import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { ItemDiaComponentsPage, ItemDiaUpdatePage } from './item-dia.page-object';

describe('ItemDia e2e test', () => {
    let navBarPage: NavBarPage;
    let itemDiaUpdatePage: ItemDiaUpdatePage;
    let itemDiaComponentsPage: ItemDiaComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load ItemDias', () => {
        navBarPage.goToEntity('item-dia');
        itemDiaComponentsPage = new ItemDiaComponentsPage();
        expect(itemDiaComponentsPage.getTitle()).toMatch(/adminhouseApp.itemDia.home.title/);
    });

    it('should load create ItemDia page', () => {
        itemDiaComponentsPage.clickOnCreateButton();
        itemDiaUpdatePage = new ItemDiaUpdatePage();
        expect(itemDiaUpdatePage.getPageTitle()).toMatch(/adminhouseApp.itemDia.home.createOrEditLabel/);
        itemDiaUpdatePage.cancel();
    });

    it('should create and save ItemDias', () => {
        itemDiaComponentsPage.clickOnCreateButton();
        itemDiaUpdatePage.tipoSelectLastOption();
        itemDiaUpdatePage.diaSelectLastOption();
        itemDiaUpdatePage.comidaSelectLastOption();
        itemDiaUpdatePage.save();
        expect(itemDiaUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
