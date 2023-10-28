export type Category = {
  id: number,
  name: string
}

export type Todo = {
  id: number,
  title: string,
  content: string,
  completed: boolean,
  category: Category
}