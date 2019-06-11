package tpo1.tests

import tpo1.PresentationPage

class FileMenuTests(page: PresentationPage) : AbstractTestSet(page) {
    override val name = "File menu"

    @Test("Should pass") fun okTest() {

    }

    @Test("Should fail") fun failingTest() {
        fail("It always fails, duh")
    }
}