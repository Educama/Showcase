package org.educama.acceptancetests.steps;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.educama.acceptancetests.pages.CasesPage;

import net.thucydides.core.annotations.Step;

public class CaseSteps {

	CasesPage casePage;

	@Step
	public void opensThePage() {
		casePage.open();
	}

	@Step
	public void openCasePage() {
		casePage.openCases();
	}

	@Step
	public void checksTheCaseList() {
		assertThat("There are cases in the case list.", casePage.getCaseList().size() == 0, is(true));
	}

	@Step
	public void checksTheCaseListForOneCase() {
		List<String> caseList = casePage.getCaseList();
		int size = caseList.size();

		assertThat("There are" + size + " case/s in the case list", size == 1, is(true));
	}

	@Step
	public void addOneCase() {
		casePage.addCase();
	}

	@Step
	public void openAddCaseModal() {
		casePage.openAddCaseModal();
	}

	@Step
	public void closeAddCaseModal() {
		casePage.closeAddCaseModal();
	}

}
