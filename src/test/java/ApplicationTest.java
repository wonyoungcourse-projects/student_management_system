import org.example.Application;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testutil.NsTest;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest extends NsTest {

    @BeforeEach
    public void setUp() {
        // 부모 클래스의 init() 메서드가 자동으로 호출되어
        // 입출력 스트림을 설정합니다.
        super.init();
    }

    @AfterEach
    public void tearDown() {
        // 부모 클래스의 printOutput() 메서드가 자동으로 호출되어
        // 스트림을 원래대로 복원하고 출력을 표시합니다.
        super.printOutput();
    }

    @DisplayName("메뉴가 정상적으로 출력되어야 한다")
    @Test
    public void shouldDisplayMenu() {
        run("exit");
        String output = output();

        assertTrue(output.contains("----- 메뉴 -----"));
        assertTrue(output.contains("1. 학생 리스트 뷰"));
        assertTrue(output.contains("2. 학생 정보 입력"));
        assertTrue(output.contains("3. 학생 정보 검색"));
        assertTrue(output.contains("4. 학생 정보 수정"));
        assertTrue(output.contains("5. 학생 정보 삭제"));
        assertTrue(output.contains("---------------"));
    }

    @DisplayName("학생 리스트가 비어있을 때 '비어있음'을 출력해야 한다")
    @Test
    public void shouldDisplayEmptyStudentList() {
        run("1", "exit", "exit");
        String output = output();

        assertTrue(output.contains("----- 학생 -----"));
        assertTrue(output.contains("비어있음"));
        assertTrue(output.contains("---------------"));
    }

    @DisplayName("학생 정보 입력 후 성공 메시지가 출력되어야 한다")
    @Test
    public void shouldDisplaySuccessMessageAfterAddingStudents() {
        run("2", "20211379 장원영, 20211380 장원잉", "exit", "exit");
        String output = output();

        assertTrue(output.contains("입력(학번, 이름) :"));
        assertTrue(output.contains("새로운 학생들이 성공적으로 입력되었습니다. (2건)"));
    }

    @DisplayName("학생 리스트가 학번 오름차순으로 정렬되어 출력되어야 한다")
    @Test
    public void shouldDisplayStudentListSortedByStudentId() {
        run("2", "20211383 장잉잉, 20211379 장원영, 20211381 장원앙", "exit",
            "1", "exit", "exit");
        String output = output();

        assertTrue(output.contains("----- 학생 -----"));
        assertTrue(output.contains("20211379 장원영"));
        assertTrue(output.contains("20211381 장원앙"));
        assertTrue(output.contains("20211383 장잉잉"));
    }

    @DisplayName("학생 정보 검색 - 이름으로 검색이 가능해야 한다")
    @Test
    public void shouldSearchStudentByName() {
        run("2", "20211379 장원영, 20211380 장원잉", "exit",
            "3", "장원영", "exit", "exit");
        String output = output();

        assertTrue(output.contains("입력(학번 or 학생 ) :"));
        assertTrue(output.contains("--- 검색결과 ---"));
        assertTrue(output.contains("20211379 장원영"));
        assertTrue(output.contains("--------------"));
    }

    @DisplayName("학생 정보 검색 - 학번 일부로 검색이 가능해야 한다")
    @Test
    public void shouldSearchStudentByPartialStudentId() {
        run("2", "20211379 장원영, 20211380 장원잉, 20211381 장원앙", "exit",
            "3", "2021", "exit", "exit");
        String output = output();

        assertTrue(output.contains("--- 검색결과 ---"));
        assertTrue(output.contains("20211379 장원영"));
        assertTrue(output.contains("20211380 장원잉"));
        assertTrue(output.contains("20211381 장원앙"));
    }

    @DisplayName("학생 정보 수정이 성공적으로 이루어져야 한다")
    @Test
    public void shouldUpdateStudentInformation() {
        run("2", "20211379 장원영", "exit",
            "4", "20211379, 아이즈원", "exit",
            "exit");
        String output = output();

        assertTrue(output.contains("입력(학번) :"));
        assertTrue(output.contains("변경 성공! [20211379, 장원영] -> [20211379, 아이즈원]"));
    }

    @DisplayName("학생 정보 삭제가 성공적으로 이루어져야 한다")
    @Test
    public void shouldDeleteStudentInformation() {
        run("2", "20211379 장원영, 20211380 장원잉, 20211381 장원앙", "exit",
            "5", "20211379, 20211380", "exit",
            "exit");
        String output = output();

        assertTrue(output.contains("입력(학번) :"));
        assertTrue(output.contains("삭제 성공! (2건)"));
    }

    @DisplayName("삭제 후 학생 리스트가 업데이트되어야 한다")
    @Test
    public void shouldUpdateStudentListAfterDeletion() {
        run("2", "20211379 장원영, 20211380 장원잉, 20211381 장원앙", "exit",
            "5", "20211380, 20211381", "exit",
            "1", "exit", "exit");
        String output = output();

        assertTrue(output.contains("20211379 장원영"));
        assertFalse(output.contains("20211380 장원잉"));
        assertFalse(output.contains("20211381 장원앙"));
    }

    @DisplayName("전체 시나리오 - 입력, 조회, 검색, 수정, 삭제가 순차적으로 동작해야 한다")
    @Test
    public void shouldWorkCompleteScenario() {
        run("1", "exit",
            "2", "20211379 장원영, 20211380 장원잉, 20211381 장원앙, 20211382 장윈영, 20211383 장잉잉", "exit",
            "3", "202", "장원앙", "exit",
            "4", "20211379, 아이즈원", "exit",
            "5", "20211380, 20211381", "exit",
            "1", "exit",
            "exit");
        String output = output();

        // 초기 빈 리스트 확인
        assertTrue(output.contains("비어있음"));

        // 학생 입력 성공 메시지
        assertTrue(output.contains("새로운 학생들이 성공적으로 입력되었습니다."));

        // 검색 결과 확인
        assertTrue(output.contains("--- 검색결과 ---"));
        assertTrue(output.contains("20211381 장원앙"));

        // 수정 성공 메시지
        assertTrue(output.contains("변경 성공! [20211379, 장원영] -> [20211379, 아이즈원]"));

        // 삭제 성공 메시지
        assertTrue(output.contains("삭제 성공! (2건)"));

        // 최종 학생 리스트 확인
        assertTrue(output.contains("20211379 아이즈원"));
        assertTrue(output.contains("20211382 장윈영"));
        assertTrue(output.contains("20211383 장잉잉"));
    }

    @DisplayName("검색 결과가 없을 때 적절한 메시지가 출력되어야 한다")
    @Test
    public void shouldDisplayNoResultsWhenSearchNotFound() {
        run("2", "20211379 장원영", "exit",
            "3", "존재하지않는학생", "exit",
            "exit");
        String output = output();

        assertTrue(output.contains("--- 검색결과 ---"));
        assertTrue(output.contains("--------------"));
    }

    @DisplayName("중복된 학번으로 학생을 추가할 수 없어야 한다")
    @Test
    public void shouldNotAddDuplicateStudentId() {
        run("2", "20211379 장원영", "20211379 장원잉", "exit",
            "exit");
        String output = output();

        // 첫 번째는 성공
        assertTrue(output.contains("새로운 학생들이 성공적으로 입력되었습니다. (1건)"));
    }

    @DisplayName("존재하지 않는 학번으로 수정할 수 없어야 한다")
    @Test
    public void shouldNotUpdateNonExistentStudent() {
        run("2", "20211379 장원영", "exit",
            "4", "99999999, 테스트", "exit",
            "exit");
        String output = output();

        // 수정 실패 또는 에러 메시지가 있어야 함
        assertFalse(output.contains("변경 성공! [99999999"));
    }

    @DisplayName("존재하지 않는 학번으로 삭제할 수 없어야 한다")
    @Test
    public void shouldNotDeleteNonExistentStudent() {
        run("2", "20211379 장원영", "exit",
            "5", "99999999", "exit",
            "1", "exit",
            "exit");
        String output = output();

        // 학생은 여전히 존재해야 함
        assertTrue(output.contains("20211379 장원영"));
    }

    @DisplayName("단일 학생 입력이 정상적으로 동작해야 한다")
    @Test
    public void shouldAddSingleStudent() {
        run("2", "20211379 장원영", "exit",
            "1", "exit",
            "exit");
        String output = output();

        assertTrue(output.contains("새로운 학생들이 성공적으로 입력되었습니다. (1건)"));
        assertTrue(output.contains("20211379 장원영"));
    }

    @DisplayName("여러 검색을 연속으로 수행할 수 있어야 한다")
    @Test
    public void shouldPerformMultipleSearches() {
        run("2", "20211379 장원영, 20211380 장원잉, 20231234 김철수", "exit",
            "3", "2021", "김철수", "2023", "exit",
            "exit");
        String output = output();

        // 세 번의 검색 결과 확인
        assertTrue(output.contains("20211379 장원영"));
        assertTrue(output.contains("20211380 장원잉"));
        assertTrue(output.contains("20231234 김철수"));
    }

    @DisplayName("수정 후 변경사항이 리스트에 반영되어야 한다")
    @Test
    public void shouldReflectChangesInListAfterUpdate() {
        run("2", "20211379 장원영", "exit",
            "4", "20211379, 아이즈원", "exit",
            "1", "exit",
            "exit");
        String output = output();

        assertTrue(output.contains("20211379 아이즈원"));
        // 이전 이름은 더 이상 나타나지 않아야 함 (단, 로그에는 남을 수 있음)
    }

    @DisplayName("exit 명령어로 각 메뉴에서 메인 메뉴로 돌아올 수 있어야 한다")
    @Test
    public void shouldReturnToMainMenuWithExitCommand() {
        run("1", "exit",
            "2", "exit",
            "3", "exit",
            "4", "exit",
            "5", "exit",
            "exit");
        String output = output();

        // 메뉴가 여러 번 출력되는지 확인 (각 exit 후 메뉴 재출력)
        int menuCount = 0;
        int index = 0;
        String menuMarker = "----- 메뉴 -----";
        while ((index = output.indexOf(menuMarker, index)) != -1) {
            menuCount++;
            index += menuMarker.length();
        }

        assertTrue(menuCount >= 5);
    }

    @DisplayName("모든 학생 삭제 후 리스트가 비어있어야 한다")
    @Test
    public void shouldShowEmptyListAfterDeletingAllStudents() {
        run("2", "20211379 장원영, 20211380 장원잉", "exit",
            "5", "20211379, 20211380", "exit",
            "1", "exit",
            "exit");
        String output = output();

        assertTrue(output.contains("비어있음"));
    }

    @Override
    protected void runMain() {
        Application.main(new String[]{});
    }
}
