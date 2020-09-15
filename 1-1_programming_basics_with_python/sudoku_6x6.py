#
# 프로그래밍기초 과제 #06
#
# 학번 : 2020076735
# 이름 : 박민선
#

def initialize_board_6x6():
    from random import shuffle
    row0 =[1,2,3,4,5,6]
    shuffle(row0)
    row1 = row0[3:6] + row0[0:3] 
    row2 = [row0[2]] + row0[0:2] + [row0[5]] + row0[3:5]
    row3 = row2[3:] + row2[:3]
    row4 = row0[1:3] + [row0[0]] + row0[4:6] + [row0[3]]
    row5 = row4[3:] + row4[:3]
    return [row0,row1,row2,row3,row4,row5]

def shuffle_ribbons(board):
    from random import shuffle
    top = board[:2]
    middle = board[2:4]
    bottom = board[4:]
    shuffle(top)
    shuffle(middle)
    shuffle(bottom)
    return top + middle + bottom

def shuffle_ribbons2(board):
    from random import shuffle
    top = board[:3]
    bottom = board[3:]
    shuffle(top)
    shuffle(bottom)
    return top + bottom

def transpose(board): # 가로/세로 뒤집기
    transposed = [[] for _ in range(len(board))]
    for j in range(len(board)):
        for i in range(len(board)):
            transposed[j].append(board[i][j])
    return transposed

def ans_board_6x6():
    board = initialize_board_6x6()
    board = shuffle_ribbons(board)
    board = transpose(board)
    board = shuffle_ribbons2(board)
    board = transpose(board)
    return board

def copy_board(board):
    ans = []
    for row in board:
        ans.append(row[:])
    return ans

def get_level():
    level = input("난이도(초급 1, 중급 2, 고급 3)를 숫자로 입력해주세요 :")
    while level not in ('1','2','3'):
        level = input("난이도(초급 1, 중급 2, 고급 3)를 숫자로 입력해주세요 :")
    if level == '1':
        return 12
    elif level == '2':
        return 18
    else:
        return 24

def make_holes(board,holes):
    from random import randint
    while holes > 0:
        i = randint(0,5)
        j = randint(0,5)
        if board[i][j] != 0:
            board[i][j] = 0
            holes -= 1
    return board

def show_board(board):
    print("S | 1 2 3 4 5 6")
    print("- - - - - - - -")
    for i in range(len(board)):
        print(i+1,"|",end=" ")
        for num in board[i]:
            if num == 0:
                print(".",end=" ")
            else:
                print(num,end=" ")
        print()

def get_integer(message,i,j):
    number = input(message)
    while not (number.isdigit() and i <= int(number) <= j):
        number = input(message)
    return int(number)

def sudoku_mini_6x6():
    ans_board = ans_board_6x6()
    puzzle_board = copy_board(ans_board)
    no_of_holes = get_level()
    puzzle_board = make_holes(puzzle_board,no_of_holes)
    show_board(puzzle_board)
    while no_of_holes > 0:
        i = get_integer("가로줄번호(1~6):",1,6) - 1
        j = get_integer("세로줄번호(1~6):",1,6) - 1
        if puzzle_board[i][j] != 0:
            print("빈칸이 아닙니다.")
            continue
        answer = int(input("숫자(1~6):"))
        if answer == ans_board[i][j]:
            puzzle_board[i][j] = answer
            show_board(puzzle_board)
            no_of_holes -= 1
        else:
            print(str(answer)+"가(이) 아닙니다. 다시 해보세요.")
    print("잘 하셨습니다. 또 들리세요.")
