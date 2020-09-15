def initialize_board_4x4(): # 4x4 크기의 스도쿠 리스트 생성
    from random import shuffle
    # row0 = shuffle([1,2,3,4]) X => shuffle은 프로시저
    row0 = [1,2,3,4]
    shuffle(row0)
    row1 = row0[2:4] + row0[0:3]
    row2 = [row0[1],row0[0],row0[3],row0[2]]
    row3 = row2[2:4] + row2[0:2]
    return [row0,row1,row2,row3]

def shuffle_ribbons(board): # 가로줄 바꾸기
    from random import shuffle
    top = board[:2] # top과 bottom은 2차원 리스트
    bottom = board[2:]
    shuffle(top)
    shuffle(bottom)
    return top + bottom

def transpose(board): # 가로/세로 뒤짚기
    transposed = [[] for _ in range(len(board))]
    for j in range(len(board)):
        for i in range(len(board)):
            transposed[j].append(board[i][j])
    return transposed

def ans_board_4x4(): # 정답 리스트 만들기
    board = initialize_board_4x4() # 스도쿠 리스트 생성
    board = shuffle_ribbons(board) # 가로줄 바꾸기
    board = transpose(board) # 가로세로 뒤짚기
    board = shuffle_ribbons(board)
    board = transpose(board)
    return board

def copy_board(board): # 사용자 퍼즐보드를 생성하기 위해 정답 리스트 복제하기
    ans = []
    for row in board:
        ans.append(row[:]) # n차원 리스트의 경우, 내부 리스트까지 하나씩 모두 복제해야 함
    return ans

def get_level():
    level = input("난이도(초급 1, 중급 2, 고급 3)를 숫자로 입력해주세요 : ")
    while level not in ('1','2','3'):
        level = input("난이도(초급 1, 중급 2, 고급 3)를 숫자로 입력해주세요 : ")
    if level == '1':
        return 6
    elif level == '2':
        return 8
    else:
        return 10

def make_holes(board,holes):
    from random import randint
    while holes > 0:
        i = randint(0,len(board)-1)
        j = randint(0,len(board)-1)
        if board[i][j] != 0:
            board[i][j] = 0
            holes -= 1
    return board

def show_board(puzzle_board): # 퍼즐보드 출력 함수
    print('S |',1,2,3,4)
    print('- | - - - -')
    for row in range(len(puzzle_board)):
        print(row+1,"|",end=" ")
        for data in puzzle_board[row]:
            if data == 0:
                print('.',end=" ")
            else:
                print(data,end=" ")
        print()

def get_integer(message, i, j):
    number = input(message)
    while not (number.isdigit() and 1 <= int(number) <= 4):
        number = input(message)
    return int(number)

def sudoku_mini():
    ans_board = ans_board_4x4()
    puzzle_board = copy_board(ans_board)
    no_of_holes = get_level()
    puzzle_board = make_holes(puzzle_board, no_of_holes)
    show_board(puzzle_board)
    while no_of_holes > 0:
        row = get_integer("가로줄번호(1~4): ",1,4) - 1
        col = get_integer("세로줄번호(1~4): ",1,4) - 1
        if puzzle_board[row][col] != 0:
            print("빈칸이 아닙니다.")
            continue
        answer = int(input("숫자(1~4):"))
        if answer == ans_board[row][col]:
            no_of_holes -= 1
            puzzle_board[row][col] = answer
            show_board(puzzle_board)
        else:
            print("%d가(이) 아닙니다. 다시 해보세요." %(answer))
    print("잘 하셨습니다. 또 들리세요.")

sudoku_mini()

####################################################

def initialize_board_6x6():
    from random import shuffle
    row0 = shuffle([1,2,3,4,5,6])
    row1 = row0[5:] + row0[:5]
    row2 = row0[4:] + row0[:4]
    row3 = row0[3:] + row0[:3]
    row4 = row0[2:] + row0[:2]
    row5 = row0[1:] + row0[:1]
    return [row0,row1,row2,row3,row4,row5]
