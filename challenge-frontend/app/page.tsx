"use client"
import { useEffect, useState } from "react"
import { TodoRow } from "./components/TodoRow";
import { Category, Todo } from "@/typings";
import { TodoRowNew } from "./components/TodoRowNew";
const todosUrl = process.env.NEXT_PUBLIC_TODOS_URL as string;
const todosByCat = process.env.NEXT_PUBLIC_TODOS_BY_CAT_URL as string;
const todosCat = process.env.NEXT_PUBLIC_CAT_URL as string;

export default function Home() {
  const [ todos, setTodos ] = useState<Todo[]>([])
  const [ categories, setCategories ] = useState<Category[]>([])
  const [ selectedCategory, setSelectedCategory ] = useState('No Filter');
  const [ addNewMode, setAddNewMode ] = useState(false);

  useEffect(() => {
    loadData()
    loadCategories()
  }, [selectedCategory])

  const loadData = async () => {
    const requestedUrl = selectedCategory === "No Filter" ? todosUrl :  todosByCat + selectedCategory
    const dataTodos = await fetch(requestedUrl).then((res) => res.json()).catch((err) => console.log(err.message))
    setTodos(dataTodos)
  }

  const loadCategories = async() => {  
    const dataCategories = await fetch(todosCat).then((res) => res.json()).catch((err) => console.log(err.message))
    if (dataCategories?.length > 0) {
      setCategories(dataCategories)
    }
  }

  const handleSelect = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setSelectedCategory(e.target.value);
  };  

  const handleAddNew = () => {
    setAddNewMode(!addNewMode)
  }

  return (
    <main className="container mx-auto px-4 py-5">
      <div className='flex items-center justify-between'>
        <h1 className='font-bold text-3xl text-[#f09e3a] my-5'>Ensolvers Challenge</h1>
        <div className='flex flex-col-reverse space-y- md:space-y-0 md:flex-row md:items-center md:space-x-2'>

          <select className="select select-sm select-ghost select-warning w-full max-w-xs" value={selectedCategory} onChange={handleSelect}>
            <option value="No Filter">No Filter</option>
            {
              categories?.map((category) => {
                return <option key={category.id} value={category.id}>{category.name}</option>
              })
            }
          </select>

          <button className="btn btn-sm btn-outline btn-warning" onClick={handleAddNew}>Add new task</button>
        </div>
      </div>
      
      <div className="overflow-x-auto">
        <table className="table">
          <thead>
            <tr>
              <th>Title</th>
              <th>Description</th>
              <th>Category</th>
              <th>Complete?</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {categories && addNewMode ? <TodoRowNew categories={categories} setAddNewMode={setAddNewMode} loadData={loadData}/> : ""}
            {
              todos?.reverse().map((todo) => {
                return <TodoRow key={todo.id} todo={todo} categories={categories}/>
              })
            }
          </tbody>
        </table>
      </div>
    </main>
  )
}
