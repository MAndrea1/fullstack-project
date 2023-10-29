import { Category, Todo } from '@/typings';
import React, { useState } from 'react'
import { getEndpoint } from "@/config-endpoints";

const { basetodosUrl } = getEndpoint();

type todo = {
  categories: Category[]
  setAddNewMode: React.Dispatch<React.SetStateAction<boolean>>
  loadData: () => Promise<void>;
}

export const TodoRowNew = ({ categories, setAddNewMode, loadData}: todo ) => {
  const [ currentContent, setCurrentContent ] = useState<Todo>({
    id: 0, // The id doesn't really matter for creating a new object
    title: '',
    content: '',
    completed: false,
    category: categories[0]
  })

  const todosPostUrl = basetodosUrl + "todo"

  const handleCheckboxChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setCurrentContent({...currentContent, [e.target.name]: e.target.checked})
  };

  const handleCategoryChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const currentCategory = categories.find((cat) => String(cat.id) === e.target.value);
    setCurrentContent({...currentContent, [e.target.name]: currentCategory})
  }

  const handleEdit = <T extends HTMLInputElement | HTMLSelectElement >(
    e: React.ChangeEvent<T>) => {
      setCurrentContent({...currentContent, [e.target.name]: e.target.value})
  }

  const handleSave = async() => {
    const newValues = {
      "title": currentContent.title,
      "content": currentContent.content,
      "completed": currentContent.completed,
      "categoryId": currentContent.category === undefined ? currentContent.category : currentContent.category.id
    }
    
    const requestOptions = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(newValues),
    };

    try {
      const response = await fetch(todosPostUrl, requestOptions);

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      const data = await response.json();
    } catch (error) {
      console.error('Error:', error);
    }
    loadData()
    setAddNewMode(false)
  }

  return (
    <tr className='shadow-md bg-white'>
      <td>
        <input
            name="title"
            type="text"
            className='border p-2'
            value={currentContent.title}
            onChange={(e) => handleEdit(e)}/>
      </td>
      <td className='md:flex'>
        <input
            name="content"
            type="text"
            className='border p-2 md:flex-1'
            value={currentContent.content}
            onChange={(e) => handleEdit(e)}/>
      </td>
      <td>
        <select 
          name="category"
          className="select select-sm select-ghost w-full max-w-xs" 
          value={currentContent.category.id} 
          onChange={(e) => handleCategoryChange(e)}>
          {
            categories?.map((category) => {
              return <option key={category.id} value={category.id}>{category.name}</option>
            })
          }
        </select>
      </td>
      <td>
        <input 
          name="completed"
          type="checkbox" 
          checked={currentContent.completed} 
          onChange={(e) => handleCheckboxChange(e)}
          className="checkbox ml-4"/>
      </td>
      <td className='w-fit'>
        <div className='flex flex-col space-y-1 md:flex-row md:space-y-0 md:space-x-1 w-fit'>
          <button onClick={handleSave} className="btn btn-sm btn-info">Save</button>
          <button onClick={() => {setAddNewMode(false)}} className="btn btn-sm btn-error">Discard</button>
        </div>
      </td>
    </tr>
  )
}
