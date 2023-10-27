import { Category, Todo } from '@/typings';
import React, { useState } from 'react'
const putUrl = process.env.NEXT_PUBLIC_PUT_URL as string;
const todosDelete = process.env.NEXT_PUBLIC_DELETE_URL as string;

type todo = {
  todo: Todo
  categories: Category[]
  deleteUser: (id: number) => Promise<void>;
}

export const TodoRow = ({todo, categories, deleteUser}: todo ) => {
  const [ currentContent, setCurrentContent ] = useState<Todo>(todo)
  const [ newContent, setNewContent ] = useState<Todo>(todo)
  const [ isChecked, setIsChecked ] = useState(newContent.completed);
  const [ editMode, setEditMode ] = useState(false)
 
  const handleCheckboxChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setIsChecked(e.target.checked);
    if (editMode) {
      setNewContent({...newContent, [e.target.name]: e.target.checked})
    } else {

    }
  };

  const handleCategoryChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const currentCategory = categories.find((cat) => String(cat.id) === e.target.value);
    setNewContent({...newContent, [e.target.name]: currentCategory})
  }

  const handleEdit = <T extends HTMLInputElement | HTMLSelectElement >(
    e: React.ChangeEvent<T>) => {
    setNewContent({...newContent, [e.target.name]: e.target.value})
  }

  const handleSave = async() => {
    if (!editMode) { return }
    const newValues = {
      "id": newContent.id,
      "title": newContent.title,
      "content": newContent.content,
      "completed": newContent.completed,
      "categoryId": newContent.category === undefined ? newContent.category : newContent.category.id
    }
    
    const requestOptions = {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(newValues),
    };

    try {
      const response = await fetch(putUrl+todo.id, requestOptions);

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      const data = await response.json();
      console.log(data);
    } catch (error) {
      console.error('Error:', error);
    }
    setCurrentContent(newContent)
    setEditMode(false)
  }

  return (
    <>
    {editMode
    ?  
    <tr>
      <td>
        <input
            name="title"
            type="text"
            className='bg-white p-2'
            value={newContent.title}
            onChange={(e) => handleEdit(e)}/>
      </td>
      <td>
        <input
            name="content"
            type="text"
            className='bg-white p-2'
            value={newContent.content}
            onChange={(e) => handleEdit(e)}/>
      </td>
      <td>
        <select 
          name="category"
          className="select select-sm select-ghost w-full max-w-xs" 
          value={newContent.category.id} 
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
          checked={newContent.completed} 
          onChange={(e) => handleCheckboxChange(e)}
          className="checkbox ml-4"/>
      </td>
      <td className='w-fit'>
        <div className='flex flex-col space-y-1 md:flex-row md:space-y-0 md:space-x-1 w-fit'>
          <button onClick={handleSave} className="btn btn-sm btn-outline btn-info">Save</button>
          <button onClick={() => {setEditMode(false), setNewContent(currentContent)}} className="btn btn-sm btn-outline btn-error">Discard</button>
        </div>
      </td>
    </tr>
    : 
    <tr>
      <td><p className='font-bold'>{currentContent.title}</p></td>
      <td><p>{currentContent.content}</p></td>
      <td><p>{currentContent.category.name}</p></td>
      <td>
        <input 
          type="checkbox" 
          checked={currentContent.completed} 
          onChange={handleCheckboxChange}
          className="checkbox ml-4"/>
      </td>
      <td className='w-fit'>
        <div className='flex flex-col space-y-1 md:flex-row md:space-y-0 md:space-x-1 w-fit'>
          <button onClick={() => setEditMode(true)} className="btn btn-sm btn-outline btn-info">Edit</button>
          <button onClick={() => deleteUser(currentContent.id)} className="btn btn-sm btn-outline btn-error">Delete</button>
        </div>
      </td>
      </tr>
    }
    </>
  )
}
